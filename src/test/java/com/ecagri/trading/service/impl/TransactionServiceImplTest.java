package com.ecagri.trading.service.impl;

import com.ecagri.trading.dto.request.TransactionRequestDto;
import com.ecagri.trading.dto.response.TransactionResponseDto;
import com.ecagri.trading.entity.Asset;
import com.ecagri.trading.entity.Portfolio;
import com.ecagri.trading.entity.Stock;
import com.ecagri.trading.entity.Transaction;
import com.ecagri.trading.repository.AssetRepository;
import com.ecagri.trading.repository.PortfolioRepository;
import com.ecagri.trading.repository.StockRepository;
import com.ecagri.trading.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private AssetRepository assetRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTransactions_shouldThrowExceptionWhenPortfolioNotFound() {

        when(portfolioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> transactionService.getTransactions(1L));
    }

    @Test
    void getTransactions_shouldGetTransactions(){
        Portfolio portfolio = new Portfolio(1L, "Long-term", null, BigDecimal.valueOf(500), null, null);

        Transaction transaction1 = new Transaction(1L, "AKBNK", "BUY", LocalDateTime.now(), BigDecimal.valueOf(240.4), BigDecimal.valueOf(1), portfolio);
        Transaction transaction2 = new Transaction(2L, "GARAN", "SELL", LocalDateTime.now(), BigDecimal.valueOf(320.0), BigDecimal.valueOf(2), portfolio);
        Transaction transaction3 = new Transaction(3L, "ISCTR", "BUY", LocalDateTime.now(), BigDecimal.valueOf(150.75), BigDecimal.valueOf(3), portfolio);

        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3);

        portfolio.setTransactions(transactions);

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolio));

        List<TransactionResponseDto> responseDtos = transactionService.getTransactions(1L);

        assertEquals(transactions.size(), responseDtos.size());

        verify(portfolioRepository).findById(1L);

    }

    @Test
    void buyStock_shouldThrowExceptionWhenStockNotFound(){

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(new Portfolio()));

        when(stockRepository.findByStockCode("")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> transactionService.buy(1L, transactionRequestDto));

    }

    @Test
    void buyStock_shouldThrowExceptionWhenPortfolioFound(){
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();

        when(portfolioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> transactionService.buy(1L, transactionRequestDto));
    }

    @Test
    void sellStock_shouldThrowExceptionWhenStockNotFound(){

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(new Portfolio()));

        when(stockRepository.findByStockCode("")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> transactionService.sell(1L, transactionRequestDto));

    }

    @Test
    void sellStock_shouldThrowExceptionWhenPortfolioFound(){
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();

        when(portfolioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> transactionService.sell(1L, transactionRequestDto));
    }

    @Test
    void buyStock_shouldThrowExceptionWhenInsufficentMoney(){
        Portfolio portfolio = new Portfolio(1L, "Long-term", null, BigDecimal.valueOf(500), null, null);

        Stock stock = new Stock(1L, "AKBNK", "AKBANK A.Ş", BigDecimal.valueOf(250.5), BigDecimal.valueOf(251.2), BigDecimal.valueOf(251.4), BigDecimal.valueOf(252.0), BigDecimal.valueOf(250.5), BigDecimal.valueOf(0.3), LocalDateTime.now());

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto("AKBNK", BigDecimal.valueOf(5));

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolio));

        when(stockRepository.findByStockCode(stock.getStockCode())).thenReturn(Optional.of(stock));

        assertThrows(RuntimeException.class, () -> transactionService.buy(1L, transactionRequestDto));

    }

    @Test
    void buyStock_shouldBuyStockWhenPortfolioNotHaveThatStock(){
        Portfolio portfolio = new Portfolio(1L, "Long-term", null, BigDecimal.valueOf(500), null, null);

        Stock stock = new Stock(1L, "AKBNK", "AKBANK A.Ş", BigDecimal.valueOf(250.5), BigDecimal.valueOf(251.2), BigDecimal.valueOf(251.4), BigDecimal.valueOf(252.0), BigDecimal.valueOf(250.5), BigDecimal.valueOf(0.3), LocalDateTime.now());

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto("AKBNK", BigDecimal.valueOf(1));

        Transaction transaction = new Transaction(1L, "AKBNK", "BUY", LocalDateTime.now(), BigDecimal.valueOf(240.4), BigDecimal.valueOf(1), portfolio);

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolio));

        when(stockRepository.findByStockCode(stock.getStockCode())).thenReturn(Optional.of(stock));

        when(assetRepository.findByStockCodeAndPortfolioPortfolioId(stock.getStockCode(), portfolio.getPortfolioId())).thenReturn(Optional.empty());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        transactionService.buy(1L, transactionRequestDto);

        verify(assetRepository, times(1)).save(any(Asset.class));

    }

    @Test
    void buyStock_shouldBuyStockWhenPortfolioAlreadyHaveThatStock(){
        Portfolio portfolio = new Portfolio(1L, "Long-term", null, BigDecimal.valueOf(500), null, null);

        Stock stock = new Stock(1L, "AKBNK", "AKBANK A.Ş", BigDecimal.valueOf(250.5), BigDecimal.valueOf(251.2), BigDecimal.valueOf(251.4), BigDecimal.valueOf(252.0), BigDecimal.valueOf(250.5), BigDecimal.valueOf(0.3), LocalDateTime.now());

        Asset asset = new Asset(1L, "AKBNK", BigDecimal.valueOf(200.2), BigDecimal.valueOf(3), portfolio);

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto("AKBNK", BigDecimal.valueOf(1));

        Transaction transaction = new Transaction(1L, "AKBNK", "BUY", LocalDateTime.now(), BigDecimal.valueOf(240.4), BigDecimal.valueOf(1), portfolio);

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolio));

        when(stockRepository.findByStockCode(stock.getStockCode())).thenReturn(Optional.of(stock));

        when(assetRepository.findByStockCodeAndPortfolioPortfolioId(stock.getStockCode(), portfolio.getPortfolioId())).thenReturn(Optional.of(asset));

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionResponseDto response = transactionService.buy(1L, transactionRequestDto);

        assertNotNull(response);

        verify(portfolioRepository, times(1)).findById(1L);

        verify(stockRepository, times(1)).findByStockCode(stock.getStockCode());

        verify(assetRepository, times(1)).findByStockCodeAndPortfolioPortfolioId(stock.getStockCode(), portfolio.getPortfolioId());

        verify(assetRepository, times(1)).save(any(Asset.class));

        verify(transactionRepository, times(1)).save(any(Transaction.class));

    }

    @Test
    void sellStock_shouldThrowExceptionWhenUserNotOwnIt(){
        Portfolio portfolio = new Portfolio(1L, "Long-term", null, BigDecimal.valueOf(500), null, null);

        Stock stock = new Stock(1L, "AKBNK", "AKBANK A.Ş", BigDecimal.valueOf(250.5), BigDecimal.valueOf(251.2), BigDecimal.valueOf(251.4), BigDecimal.valueOf(252.0), BigDecimal.valueOf(250.5), BigDecimal.valueOf(0.3), LocalDateTime.now());

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto("AKBNK", BigDecimal.valueOf(1));

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolio));

        when(stockRepository.findByStockCode(stock.getStockCode())).thenReturn(Optional.of(stock));

        when(assetRepository.findByStockCodeAndPortfolioPortfolioId(stock.getStockCode(), portfolio.getPortfolioId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> transactionService.sell(1L, transactionRequestDto));

    }

    @Test
    void sellStock_shouldDeleteAssetIfRemainingStockZero(){
        Portfolio portfolio = new Portfolio(1L, "Long-term", null, BigDecimal.valueOf(500), null, null);

        Stock stock = new Stock(1L, "AKBNK", "AKBANK A.Ş", BigDecimal.valueOf(250.5), BigDecimal.valueOf(251.2), BigDecimal.valueOf(251.4), BigDecimal.valueOf(252.0), BigDecimal.valueOf(250.5), BigDecimal.valueOf(0.3), LocalDateTime.now());

        Asset asset = new Asset(1L, "AKBNK", BigDecimal.valueOf(200.2), BigDecimal.valueOf(1), portfolio);

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto("AKBNK", BigDecimal.valueOf(1));

        Transaction transaction = new Transaction(1L, "AKBNK", "BUY", LocalDateTime.now(), BigDecimal.valueOf(240.4), BigDecimal.valueOf(1), portfolio);

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolio));

        when(stockRepository.findByStockCode(stock.getStockCode())).thenReturn(Optional.of(stock));

        when(assetRepository.findByStockCodeAndPortfolioPortfolioId(stock.getStockCode(), portfolio.getPortfolioId())).thenReturn(Optional.of(asset));

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        transactionService.sell(1L, transactionRequestDto);

        verify(assetRepository, times(1)).delete(any(Asset.class));
    }

    @Test
    void sellStock_shouldDecreaseAssetQuantityAndAdjustAverageCost(){
        Portfolio portfolio = new Portfolio(1L, "Long-term", null, BigDecimal.valueOf(500), null, null);

        Stock stock = new Stock(1L, "AKBNK", "AKBANK A.Ş", BigDecimal.valueOf(250.5), BigDecimal.valueOf(251.2), BigDecimal.valueOf(251.4), BigDecimal.valueOf(252.0), BigDecimal.valueOf(250.5), BigDecimal.valueOf(0.3), LocalDateTime.now());

        Asset asset = new Asset(1L, "AKBNK", BigDecimal.valueOf(200.2), BigDecimal.valueOf(3), portfolio);

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto("AKBNK", BigDecimal.valueOf(1));

        Transaction transaction = new Transaction(1L, "AKBNK", "BUY", LocalDateTime.now(), BigDecimal.valueOf(240.4), BigDecimal.valueOf(1), portfolio);

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolio));

        when(stockRepository.findByStockCode(stock.getStockCode())).thenReturn(Optional.of(stock));

        when(assetRepository.findByStockCodeAndPortfolioPortfolioId(stock.getStockCode(), portfolio.getPortfolioId())).thenReturn(Optional.of(asset));

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        transactionService.sell(1L, transactionRequestDto);

        verify(assetRepository, times(1)).save(any(Asset.class));
    }

}