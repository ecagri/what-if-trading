package com.ecagri.trading.service.impl;

import com.ecagri.trading.dto.PortfolioRequestDto;
import com.ecagri.trading.dto.PortfolioResponseDto;
import com.ecagri.trading.entity.Account;
import com.ecagri.trading.entity.Asset;
import com.ecagri.trading.entity.Portfolio;
import com.ecagri.trading.entity.Transaction;
import com.ecagri.trading.mapper.PortfolioMapper;
import com.ecagri.trading.repository.AccountRepository;
import com.ecagri.trading.repository.AssetRepository;
import com.ecagri.trading.repository.PortfolioRepository;
import com.ecagri.trading.repository.TransactionRepository;
import com.ecagri.trading.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final AccountRepository accountRepository;
    private final PortfolioRepository portfolioRepository;
    private AssetRepository assetRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public PortfolioServiceImpl(PortfolioRepository portfolioRepository, AccountRepository accountRepository, AssetRepository assetRepository, TransactionRepository transactionRepository) {
        this.portfolioRepository = portfolioRepository;
        this.accountRepository = accountRepository;
        this.assetRepository = assetRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public PortfolioResponseDto getPortfolio(Long portfolioId) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);

        if(portfolio.isEmpty()){
            throw new IllegalArgumentException("Portfolio does not exist.");
        }

        return PortfolioMapper.toPortfolioDto(portfolio.get());

    }

    @Override
    public PortfolioResponseDto createPortfolio(Long accountId, PortfolioRequestDto portfolioDto) {
        Portfolio portfolio = PortfolioMapper.toPortfolio(portfolioDto);

        Optional<Account> account = accountRepository.findById(accountId);

        if(account.isEmpty()){
            throw new IllegalArgumentException("Account does not exist.");
        }

        portfolio.setAccount(account.get());

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        return PortfolioMapper.toPortfolioDto(savedPortfolio);

    }

    @Override
    public PortfolioResponseDto updatePortfolio(Long portfolioId, String portfolioName, BigDecimal balance) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);

        if(portfolio.isEmpty()){
            throw new IllegalArgumentException("Portfolio does not exist.");
        }

        Portfolio portfolioToUpdate = portfolio.get();

        portfolioToUpdate.setPortfolioName(portfolioName);

        portfolioToUpdate.setBalance(balance);

        return PortfolioMapper.toPortfolioDto(portfolioRepository.save(portfolioToUpdate));
    }

    @Override
    public void deletePortfolio(Long portfolioId) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);

        if(portfolio.isEmpty()){
            throw new IllegalArgumentException("Portfolio does not exist.");
        }

        List<Asset> assets = portfolio.get().getAssets();
        if (assets != null) {
            assetRepository.deleteAll(assets);
        }
        List<Transaction> transactions = portfolio.get().getTransactions();
        if (transactions != null) {
            transactionRepository.deleteAll(transactions);
        }

        portfolioRepository.delete(portfolio.get());
    }

    @Override
    public List<PortfolioResponseDto> getPortfolios(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);

        if(account.isEmpty()){
            throw new IllegalArgumentException("Account does not exist.");
        }

        return account.get().getPortfolios().stream().map(PortfolioMapper::toPortfolioDto).collect(Collectors.toList());
    }

    public List<PortfolioResponseDto> getPortfolios(){
        return portfolioRepository.findAll().stream().map(PortfolioMapper::toPortfolioDto).collect(Collectors.toList());
    }
}
