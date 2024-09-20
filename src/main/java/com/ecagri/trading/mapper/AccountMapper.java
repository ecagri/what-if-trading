package com.ecagri.trading.mapper;

import com.ecagri.trading.dto.AccountRequestDto;
import com.ecagri.trading.dto.AccountResponseDto;
import com.ecagri.trading.entity.Account;

import java.util.stream.Collectors;

public class AccountMapper {
    public static AccountResponseDto toAccountDto(Account account) {
        AccountResponseDto accountDto = new AccountResponseDto(
                account.getAccountId(),
                account.getAccountOwnerFullName(),
                account.getAccountOwnerId(),
                account.getPortfolios() == null ? null : account.getPortfolios().stream().map(PortfolioMapper::toPortfolioDto).collect(Collectors.toList())
        );
        return accountDto;
    }
    public static Account toAccount(AccountRequestDto accountDto) {
        Account account = new Account(
                accountDto.getAccountOwnerFullName(),
                accountDto.getAccountOwnerId()
        );
        return account;
    }
}
