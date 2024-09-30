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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PortfolioServiceImplTest {

    @InjectMocks
    private PortfolioServiceImpl portfolioService;

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPortfolioById_shouldReturnPortfolioResponseDto() {
        Portfolio portfolio = new Portfolio(1L, "Long-term", null, BigDecimal.valueOf(500), null, null);

        PortfolioResponseDto expectedPortfolioResponseDto = new PortfolioResponseDto();

        try (MockedStatic<PortfolioMapper> utilities = Mockito.mockStatic(PortfolioMapper.class)) {
            when(portfolioRepository.findById(portfolio.getPortfolioId())).thenReturn(Optional.of(portfolio));

            utilities.when(() -> PortfolioMapper.toPortfolioDto(portfolio)).thenReturn(expectedPortfolioResponseDto);

            PortfolioResponseDto actualPortfolioResponseDto = portfolioService.getPortfolio(portfolio.getPortfolioId());

            assertEquals(expectedPortfolioResponseDto, actualPortfolioResponseDto);
        }
    }

    @Test
    void getPortfolioById_shouldThrowExceptionWhenPortfolioNotFound() {

        Long portfloioId = 1L;

        when(portfolioRepository.findById(portfloioId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> portfolioService.getPortfolio(portfloioId));
    }

    @Test
    void createPortfolio_shouldThrowExceptionWhenAccountNotFound() {

        PortfolioRequestDto portfolio = new PortfolioRequestDto("Long-term", BigDecimal.valueOf(500));

        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> portfolioService.createPortfolio(accountId, portfolio));
    }

    @Test
    void createPortfolio_shouldCreatePortfolioWhenAccountFound(){

        PortfolioRequestDto portfolioRequestDto = new PortfolioRequestDto("Long-term", BigDecimal.valueOf(500));

        Account account = new Account(1L, "Çağrı Çaycı", 13425786483L, null);

        Portfolio portfolio = new Portfolio(1L, "Long-term", null, BigDecimal.valueOf(500), null, null);

        Portfolio savedPortfolio = new Portfolio(1L, "Long-term", account, BigDecimal.valueOf(500), null, null);

        when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));

        when(portfolioRepository.save(any(Portfolio.class))).thenReturn(savedPortfolio);

        PortfolioResponseDto expectedPortfolioResponseDto = PortfolioMapper.toPortfolioDto(portfolio);

        PortfolioResponseDto actualPortfolioResponseDto = portfolioService.createPortfolio(account.getAccountId(), portfolioRequestDto);

        assertEquals(expectedPortfolioResponseDto, actualPortfolioResponseDto);

    }

    @Test
    void updatePortfolio_shouldThrowExceptionWhenPortfolioNotFound() {
        Long portfloioId = 1L;

        String newPortfolioName = "Long-term";

        BigDecimal newPrice = BigDecimal.valueOf(500);

        when(portfolioRepository.findById(portfloioId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> portfolioService.updatePortfolio(portfloioId, newPortfolioName, newPrice));
    }

    @Test
    void updatePortfolio_shouldUpdatePortfolioNameOrBalance(){

        Portfolio portfolio = new Portfolio(1L, "Long-term", null, BigDecimal.valueOf(500), null, null);

        Portfolio updatedPortfolio = new Portfolio(1L, "Short-term", null, BigDecimal.valueOf(250), null, null);

        when(portfolioRepository.findById(portfolio.getPortfolioId())).thenReturn(Optional.of(portfolio));

        when(portfolioRepository.save(any(Portfolio.class))).thenReturn(updatedPortfolio);

        PortfolioResponseDto expectedPortfolioResponseDto = PortfolioMapper.toPortfolioDto(updatedPortfolio);

        PortfolioResponseDto actualPortfolioResponseDto = portfolioService.updatePortfolio(portfolio.getPortfolioId(), updatedPortfolio.getPortfolioName(), updatedPortfolio.getBalance());

        assertEquals(expectedPortfolioResponseDto, actualPortfolioResponseDto);

    }

    @Test
    void deletePortfolio_shouldThrowExceptionWhenPortfolioNotFound() {
        Long portfloioId = 1L;

        when(portfolioRepository.findById(portfloioId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> portfolioService.deletePortfolio(portfloioId));
    }

    @Test
    void deletePortfolio_shouldDeletePortfolioIfNoAssetsAndTransactions(){

        Portfolio portfolio = new Portfolio(1L, "Long-term", null, BigDecimal.valueOf(500), null, null);

        when(portfolioRepository.findById(portfolio.getPortfolioId())).thenReturn(Optional.of(portfolio));

        portfolioService.deletePortfolio(portfolio.getPortfolioId());

        verify(portfolioRepository).delete(portfolio);

    }

    @Test
    void deletePortfolio_shouldDeletePortfolioWithAssetsAndTransactions() {
        Portfolio portfolio = new Portfolio(1L, "Long-term", null, BigDecimal.valueOf(500), null, null);

        Asset asset1 = new Asset(1L, "AKBNK", BigDecimal.valueOf(3), BigDecimal.valueOf(8), portfolio);
        Asset asset2 = new Asset(2L, "THYAO", BigDecimal.valueOf(5), BigDecimal.valueOf(10), portfolio);
        Asset asset3 = new Asset(3L, "TUPRS", BigDecimal.valueOf(32), BigDecimal.valueOf(10), portfolio);

        Transaction transaction1 = new Transaction(1L, "AKBNK", "BUY", LocalDateTime.now(), BigDecimal.valueOf(3), BigDecimal.valueOf(5), portfolio);
        Transaction transaction2 = new Transaction(2L, "THYAO", "SELL", LocalDateTime.now(), BigDecimal.valueOf(5), BigDecimal.valueOf(7), portfolio);

        portfolio.setAssets(Arrays.asList(asset1, asset2, asset3));

        portfolio.setTransactions(Arrays.asList(transaction1, transaction2));

        when(portfolioRepository.findById(portfolio.getPortfolioId())).thenReturn(Optional.of(portfolio));

        portfolioService.deletePortfolio(portfolio.getPortfolioId());

        verify(portfolioRepository).delete(portfolio);

        verify(assetRepository).deleteAll(Arrays.asList(asset1, asset2, asset3));

        verify(transactionRepository).deleteAll(Arrays.asList(transaction1, transaction2));

    }

    @Test
    void getPortfoliosWithoutAccountId_shouldReturnListOfPortfolioResponseDto() {

        Portfolio portfolio1 = new Portfolio(1L, "Long-term", null, BigDecimal.valueOf(500), null, null);
        Portfolio portfolio2 = new Portfolio(2L, "Short-term", null, BigDecimal.valueOf(1000), null, null);
        Portfolio portfolio3 = new Portfolio(3L, "Balanced", null, BigDecimal.valueOf(750), null, null);

        Asset asset1 = new Asset(1L, "AKBNK", BigDecimal.valueOf(3), BigDecimal.valueOf(8), portfolio1);
        Asset asset2 = new Asset(2L, "THYAO", BigDecimal.valueOf(5), BigDecimal.valueOf(10), portfolio1);
        Asset asset3 = new Asset(3L, "TUPRS", BigDecimal.valueOf(32), BigDecimal.valueOf(10), portfolio2);

        Transaction transaction1 = new Transaction(1L, "AKBNK", "BUY", LocalDateTime.now(), BigDecimal.valueOf(3), BigDecimal.valueOf(5), portfolio1);
        Transaction transaction2 = new Transaction(2L, "THYAO", "SELL", LocalDateTime.now(), BigDecimal.valueOf(5), BigDecimal.valueOf(7), portfolio2);

        portfolio1.setAssets(Arrays.asList(asset1, asset2));
        portfolio2.setAssets(Arrays.asList(asset3));

        portfolio1.setTransactions(Arrays.asList(transaction1));
        portfolio2.setTransactions(Arrays.asList(transaction2));

        when(portfolioRepository.findAll()).thenReturn(Arrays.asList(portfolio1, portfolio2, portfolio3));

        List<PortfolioResponseDto> result = portfolioService.getPortfolios();

        assertEquals(result, Stream.of(portfolio1, portfolio2, portfolio3).map(PortfolioMapper::toPortfolioDto).collect(Collectors.toList()));
    }

    @Test
    void getPortfoliosWithAccountId_shouldReturnListOfPortfolioResponseDto() {
        Account account1 = new Account(1L, "Çağrı Çaycı", 13425786483L, null);
        Account account2 = new Account(2L, "Çağrı Çaycı 1", 13425786484L, null);

        Portfolio portfolio1 = new Portfolio(1L, "Long-term", account1, BigDecimal.valueOf(500), null, null);
        Portfolio portfolio2 = new Portfolio(2L, "Short-term", account2, BigDecimal.valueOf(1000), null, null);
        Portfolio portfolio3 = new Portfolio(3L, "Balanced", account1, BigDecimal.valueOf(750), null, null);

        Asset asset1 = new Asset(1L, "AKBNK", BigDecimal.valueOf(3), BigDecimal.valueOf(8), portfolio1);
        Asset asset2 = new Asset(2L, "THYAO", BigDecimal.valueOf(5), BigDecimal.valueOf(10), portfolio1);
        Asset asset3 = new Asset(3L, "TUPRS", BigDecimal.valueOf(32), BigDecimal.valueOf(10), portfolio2);

        Transaction transaction1 = new Transaction(1L, "AKBNK", "BUY", LocalDateTime.now(), BigDecimal.valueOf(3), BigDecimal.valueOf(5), portfolio1);
        Transaction transaction2 = new Transaction(2L, "THYAO", "SELL", LocalDateTime.now(), BigDecimal.valueOf(5), BigDecimal.valueOf(7), portfolio2);

        portfolio1.setAssets(Arrays.asList(asset1, asset2));
        portfolio2.setAssets(Arrays.asList(asset3));

        portfolio1.setTransactions(Arrays.asList(transaction1));
        portfolio2.setTransactions(Arrays.asList(transaction2));

        account1.setPortfolios(Arrays.asList(portfolio1, portfolio3));
        account2.setPortfolios(Arrays.asList(portfolio2));

        when(accountRepository.findById(account1.getAccountId())).thenReturn(Optional.of(account1));

        List<PortfolioResponseDto> result = portfolioService.getPortfolios(account1.getAccountId());

        assertEquals(result, Stream.of(portfolio1, portfolio3).map(PortfolioMapper::toPortfolioDto).collect(Collectors.toList()));
    }

    @Test
    void getPortfoliosWithAccountId_shouldThrowExceptionWhenAccountNotFound() {
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> portfolioService.getPortfolios(accountId));

    }
}