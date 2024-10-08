package com.ecagri.trading.service.impl;

import com.ecagri.trading.dto.request.AccountRequestDto;
import com.ecagri.trading.dto.response.AccountResponseDto;
import com.ecagri.trading.entity.Account;
import com.ecagri.trading.entity.Portfolio;
import com.ecagri.trading.mapper.AccountMapper;
import com.ecagri.trading.repository.AccountRepository;
import com.ecagri.trading.service.AccountService;
import com.ecagri.trading.service.PortfolioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PortfolioService portfolioService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PortfolioService portfolioService, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.portfolioService = portfolioService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AccountResponseDto createAccount(AccountRequestDto accountDto) {
        if(accountRepository.findByAccountOwnerId(accountDto.getAccountOwnerId()).isPresent()){
            throw new RuntimeException("The account already exists.");
        }

        Account account = AccountMapper.toAccount(accountDto);

        account.setAccountOwnerPassword(passwordEncoder.encode(accountDto.getAccountOwnerPassword()));

        Account savedAccount = accountRepository.save(account);

        return AccountMapper.toAccountDto(savedAccount);
    }

    @Override
    public List<AccountResponseDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();

        return accounts.stream().map(AccountMapper::toAccountDto).collect(Collectors.toList());
    }

    @Override
    public AccountResponseDto getAccount(Long customer_id){
        Optional<Account> account = accountRepository.findByAccountOwnerId(customer_id);

        if (account.isEmpty()){
            throw new IllegalArgumentException("Account not exist!");
        }

        return AccountMapper.toAccountDto(account.orElse(null));
    }

    @Transactional
    @Override
    public void deleteAccount(Long customerId) {
        Optional<Account> account = accountRepository.findByAccountOwnerId(customerId);

        if (account.isEmpty()) {
            throw new IllegalArgumentException("Account not exist!");
        }

        List<Portfolio> portfolios = account.get().getPortfolios();
        if (portfolios != null) {
            for (Portfolio portfolio : portfolios) {
                portfolioService.deletePortfolio(portfolio.getPortfolioId());
            }
        }

        accountRepository.delete(account.get());
    }

    @Override
    public AccountResponseDto updateAccount(Long customerId, String account_owner_name) {
        Optional<Account> account = accountRepository.findByAccountOwnerId(customerId);

        if (account.isEmpty()){
            throw new IllegalArgumentException("Account not exist!");
        }

        account.get().setAccountOwnerFullName(account_owner_name);

        Account savedAccount = accountRepository.save(account.get());

        return AccountMapper.toAccountDto(savedAccount);

    }
}
