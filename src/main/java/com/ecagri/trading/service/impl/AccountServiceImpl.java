package com.ecagri.trading.service.impl;

import com.ecagri.trading.dto.AccountRequestDto;
import com.ecagri.trading.dto.AccountResponseDto;
import com.ecagri.trading.entity.Account;
import com.ecagri.trading.entity.Portfolio;
import com.ecagri.trading.mapper.AccountMapper;
import com.ecagri.trading.repository.AccountRepository;
import com.ecagri.trading.service.AccountService;
import com.ecagri.trading.service.PortfolioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PortfolioService portfolioService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PortfolioService portfolioService) {
        this.accountRepository = accountRepository;
        this.portfolioService = portfolioService;
    }

    @Override
    public AccountResponseDto createAccount(AccountRequestDto accountDto) {
        Account account = AccountMapper.toAccount(accountDto);

        Account savedAccount = accountRepository.save(account);

        return AccountMapper.toAccountDto(savedAccount);
    }

    @Override
    public List<AccountResponseDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();

        List<AccountResponseDto> accountDtos = accounts.stream().map(AccountMapper::toAccountDto).collect(Collectors.toList());

        return accountDtos;
    }

    @Override
    public AccountResponseDto getAccount(Long customer_id){
        Account account = accountRepository.findByAccountOwnerId(customer_id);

        if (account == null) {
            throw new IllegalArgumentException("Account not exist!");
        }

        return AccountMapper.toAccountDto(account);
    }

    @Transactional
    @Override
    public void deleteAccount(Long customerId) {
        Account account = accountRepository.findByAccountOwnerId(customerId);

        if (account != null) {
            List<Portfolio> portfolios = account.getPortfolios();

            if (portfolios != null) {
                for (Portfolio portfolio : portfolios) {
                    portfolioService.deletePortfolio(portfolio.getPortfolioId());
                }
            }

            accountRepository.delete(account);
        }
    }

    @Override
    public AccountResponseDto updateAccount(Long customerId, String account_owner_name) {
        Account account = accountRepository.findByAccountOwnerId(customerId);

        if (account == null){
            throw new IllegalArgumentException("Account not exist!");
        }

        account.setAccountOwnerFullName(account_owner_name);

        Account savedAccount = accountRepository.save(account);

        return AccountMapper.toAccountDto(savedAccount);

    }
}
