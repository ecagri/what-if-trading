package com.ecagri.trading.service.impl;

import com.ecagri.trading.dto.request.AccountRequestDto;
import com.ecagri.trading.dto.response.AccountResponseDto;
import com.ecagri.trading.entity.Account;
import com.ecagri.trading.entity.Asset;
import com.ecagri.trading.entity.Portfolio;
import com.ecagri.trading.entity.Transaction;
import com.ecagri.trading.mapper.AccountMapper;
import com.ecagri.trading.repository.AccountRepository;
import com.ecagri.trading.repository.PortfolioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PortfolioRepository portfolioRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private PortfolioServiceImpl portfolioService;

    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        account = new Account("Çağrı Çaycı", 13425786483L);
    }

    @Test
    void createAccount_shouldReturnAccountResponseDto() {
        AccountRequestDto requestDto = new AccountRequestDto(
                12345678910L,
                "Çağrı Çaycı",
                "SecurePass123!",
                "cagri@example.com",
                LocalDate.now()
        );
        Account accountEntity = new Account();
        Account savedAccountEntity = new Account();
        AccountResponseDto expectedResponseDto = new AccountResponseDto();

        try (MockedStatic<AccountMapper> utilities = Mockito.mockStatic(AccountMapper.class)) {
            utilities.when(() -> AccountMapper.toAccount(requestDto)).thenReturn(accountEntity);
            when(accountRepository.save(any(Account.class))).thenReturn(savedAccountEntity);
            utilities.when(() -> AccountMapper.toAccountDto(savedAccountEntity)).thenReturn(expectedResponseDto);

            AccountResponseDto actualResponseDto = accountService.createAccount(requestDto);
            assertEquals(expectedResponseDto, actualResponseDto);
        }
    }

    @Test
    void getAllAccounts_shouldReturnListOfAccountResponseDto() {
        Account account1 = new Account("Çağrı Çaycı 1", 1L);
        Account account2 = new Account("Çağrı Çaycı 2", 2L);

        when(accountRepository.findAll()).thenReturn(Arrays.asList(account1, account2));

        List<AccountResponseDto> result = accountService.getAllAccounts();

        assertEquals(result, Arrays.asList(AccountMapper.toAccountDto(account1), AccountMapper.toAccountDto(account2)));

    }

    @Test
    void getAccountById_shouldReturnAccountResponseDto() {

        AccountResponseDto expectedResponseDto = new AccountResponseDto();

        try (MockedStatic<AccountMapper> utilities = Mockito.mockStatic(AccountMapper.class)) {
            when(accountRepository.findByAccountOwnerId(account.getAccountOwnerId())).thenReturn(Optional.of(account));

            utilities.when(() -> AccountMapper.toAccountDto(account)).thenReturn(expectedResponseDto);

            AccountResponseDto accountResponseDto = accountService.getAccount(account.getAccountOwnerId());

            assertEquals(expectedResponseDto, accountResponseDto);
        }
    }

    @Test
    void getAccountById_shouldThrowExceptionWhenNoUser(){
        long accountId = 13425786483L;

        when(accountRepository.findByAccountOwnerId(accountId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> accountService.getAccount(accountId));
    }

    @Test
    void updateAccount_shouldReturnAccountResponseDto() {
        String accountOwnerName = "Çağrı Çaycı 1";

        when(accountRepository.findByAccountOwnerId(account.getAccountOwnerId())).thenReturn(Optional.of(account));

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountResponseDto expectedResponseDto = accountService.updateAccount(account.getAccountOwnerId(), accountOwnerName);

        assertEquals(expectedResponseDto.getAccountOwnerFullName(), accountOwnerName);

    }

    @Test
    void updateAccount_shouldThrowExceptionWhenNoUser(){

        String accountOwnerName = "Çağrı Çaycı 1";

        when(accountRepository.findByAccountOwnerId(account.getAccountOwnerId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> accountService.updateAccount(account.getAccountOwnerId(), accountOwnerName));

    }

    @Test
    void deleteAccount_shouldThrowExceptionWhenNoUser(){

        when(accountRepository.findByAccountOwnerId(account.getAccountOwnerId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> accountService.deleteAccount(account.getAccountOwnerId()));
    }

    @Test
    void deleteAccount_shouldRemoveAccountIfNoPortfoliosExist() {

        when(accountRepository.findByAccountOwnerId(account.getAccountOwnerId())).thenReturn(Optional.of(account));

        accountService.deleteAccount(account.getAccountOwnerId());

        verify(accountRepository).delete(account);
    }

    @Test
    void deleteAccount_shouldRemoveAccountWithPortfolios(){

        Portfolio portfolio1 = new Portfolio(1L, "Long-term", account, BigDecimal.valueOf(500), null, null);
        Portfolio portfolio2 = new Portfolio(2L, "Short-term", account, BigDecimal.valueOf(1000), null, null);

        Asset asset1 = new Asset(1L, "AKBNK", BigDecimal.valueOf(3), BigDecimal.valueOf(8), portfolio1);
        Asset asset2 = new Asset(2L, "THYAO", BigDecimal.valueOf(5), BigDecimal.valueOf(10), portfolio1);
        Asset asset3 = new Asset(3L, "TUPRS", BigDecimal.valueOf(32), BigDecimal.valueOf(10), portfolio2);

        Transaction transaction1 = new Transaction(1L, "AKBNK", "BUY", LocalDateTime.now(), BigDecimal.valueOf(3), BigDecimal.valueOf(5), portfolio1);
        Transaction transaction2 = new Transaction(2L, "THYAO", "SELL", LocalDateTime.now(), BigDecimal.valueOf(5), BigDecimal.valueOf(7), portfolio1);

        portfolio1.setAssets(Arrays.asList(asset1, asset2));
        portfolio2.setAssets(Arrays.asList(asset3));

        portfolio1.setTransactions(Arrays.asList(transaction1, transaction2));

        account.setPortfolios(Arrays.asList(portfolio1, portfolio2));

        when(accountRepository.findByAccountOwnerId(account.getAccountOwnerId())).thenReturn(Optional.of(account));
        when(portfolioRepository.findById(portfolio1.getPortfolioId())).thenReturn(Optional.of(portfolio1));
        when(portfolioRepository.findById(portfolio2.getPortfolioId())).thenReturn(Optional.of(portfolio2));

        accountService.deleteAccount(13425786483L);

        verify(portfolioService).deletePortfolio(portfolio1.getPortfolioId());
        verify(portfolioService).deletePortfolio(portfolio2.getPortfolioId());

        verify(accountRepository).delete(account);

    }

}
