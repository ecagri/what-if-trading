package com.ecagri.trading.service.impl;

import com.ecagri.trading.dto.request.TransactionRequestDto;
import com.ecagri.trading.dto.response.TransactionResponseDto;
import com.ecagri.trading.entity.*;
import com.ecagri.trading.mapper.TransactionMapper;
import com.ecagri.trading.repository.AssetRepository;
import com.ecagri.trading.repository.PortfolioRepository;
import com.ecagri.trading.repository.StockRepository;
import com.ecagri.trading.repository.TransactionRepository;
import com.ecagri.trading.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final PortfolioRepository portfolioRepository;

    private final AssetRepository assetRepository;

    private final StockRepository stockRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, PortfolioRepository portfolioRepository, AssetRepository assetRepository, StockRepository stockRepository) {
        this.transactionRepository = transactionRepository;
        this.portfolioRepository = portfolioRepository;
        this.assetRepository = assetRepository;
        this.stockRepository = stockRepository;
    }


    @Override
    public TransactionResponseDto buy(Long portfolioId, TransactionRequestDto transactionDto) {
        Transaction transaction = TransactionMapper.toTransaction(transactionDto);

        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);

        Optional<Stock> stock = stockRepository.findByStockCode(transactionDto.getStockCode());

        if(portfolio.isEmpty()){
            throw new IllegalArgumentException("Portfolio does not exist.");
        }
        if(stock.isEmpty()){
            throw new IllegalArgumentException("Stock does not exist.");
        }

        transaction.setPortfolio(portfolio.get());

        transaction.setStockPrice(stock.get().getPrice());

        transaction.setTransactionDate(LocalDateTime.now());

        transaction.setTransactionType("buy");

        if(transaction.getStockPrice().multiply(transaction.getStockQuantity()).compareTo(portfolio.get().getBalance()) > 0) {
            throw new RuntimeException("Insufficient money");
        }

        Optional<Asset> asset = assetRepository.findByStockCodeAndPortfolioPortfolioId(transaction.getStockCode(), portfolio.get().getPortfolioId());

        if (asset.isEmpty()) {
            asset = Optional.of(new Asset());
            asset.get().setStockCode(transaction.getStockCode());
            asset.get().setPortfolio(portfolio.get());
            asset.get().setQuantity(transaction.getStockQuantity());
            asset.get().setAveragePrice(transaction.getStockPrice());
        } else {
            asset.get().setAveragePrice(calculateNewAveragePrice(
                    asset.get().getAveragePrice(),
                    asset.get().getQuantity(),
                    transaction.getStockQuantity(),
                    transaction.getStockPrice()
            ));
            asset.get().setQuantity(asset.get().getQuantity().add(transaction.getStockQuantity()));
        }

        assetRepository.save(asset.get());

        portfolio.get().setBalance(portfolio.get().getBalance().subtract(transaction.getStockQuantity().multiply(transaction.getStockPrice())));

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.toTransactionDto(savedTransaction);
    }

    @Override
    public TransactionResponseDto sell(Long portfolioId, TransactionRequestDto transactionDto) {
        Transaction transaction = TransactionMapper.toTransaction(transactionDto);

        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);

        Optional<Stock> stock = stockRepository.findByStockCode(transaction.getStockCode());

        if(portfolio.isEmpty()){
            throw new IllegalArgumentException("Portfolio does not exist.");
        }
        if(stock.isEmpty()){
            throw new IllegalArgumentException("Stock does not exist.");
        }

        transaction.setPortfolio(portfolio.get());

        transaction.setStockPrice(stock.get().getPrice());

        transaction.setTransactionDate(LocalDateTime.now());

        transaction.setTransactionType("sell");

        Optional<Asset> asset = assetRepository.findByStockCodeAndPortfolioPortfolioId(transaction.getStockCode(), portfolio.get().getPortfolioId());

        if (asset.isEmpty() || asset.get().getQuantity().compareTo(transaction.getStockQuantity()) < 0) {
            throw new RuntimeException("Insufficient stock for stock code " + transaction.getStockCode() + " in portfolio.");
        }

        asset.get().setQuantity(asset.get().getQuantity().subtract(transaction.getStockQuantity()));

        portfolio.get().setBalance(portfolio.get().getBalance().add(transaction.getStockQuantity().multiply(transaction.getStockPrice())));
        if(asset.get().getQuantity().compareTo(BigDecimal.ZERO) == 0){
            assetRepository.delete(asset.get());
        }else{
            asset.get().setAveragePrice(calculateNewAveragePrice(
                    asset.get().getAveragePrice(),
                    asset.get().getQuantity(),
                    transaction.getStockQuantity().negate(),
                    transaction.getStockPrice()
            ));

            assetRepository.save(asset.get());

        }

        transactionRepository.save(transaction);

        return TransactionMapper.toTransactionDto(transaction);

    }

    @Override
    public List<TransactionResponseDto> getTransactions(Long portfolioId) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);

        if(portfolio.isEmpty()){
            throw new IllegalArgumentException("Portfolio does not exist.");
        }

        return Optional.ofNullable(portfolio.get().getTransactions()).orElse(Collections.emptyList()).stream().map(TransactionMapper::toTransactionDto).collect(Collectors.toList());
    }

    private BigDecimal calculateNewAveragePrice(BigDecimal currentAveragePrice, BigDecimal currentQuantity, BigDecimal transactionQuantity, BigDecimal transactionPrice) {
        return currentAveragePrice.multiply(currentQuantity)
                .add(transactionPrice.multiply(transactionQuantity))
                .divide(currentQuantity.add(transactionQuantity), RoundingMode.HALF_UP);
    }

}
