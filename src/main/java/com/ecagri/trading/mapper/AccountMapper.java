package com.ecagri.trading.mapper;

import com.ecagri.trading.dto.request.AccountRequestDto;
import com.ecagri.trading.dto.response.AccountResponseDto;
import com.ecagri.trading.entity.Account;
import com.ecagri.trading.model.Role;
import com.ecagri.trading.util.DataMaskingUtil;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class AccountMapper {
    public static AccountResponseDto toAccountDto(Account account) {
        AccountResponseDto accountDto = new AccountResponseDto(
                DataMaskingUtil.maskAccountOwnerId(account.getAccountOwnerId()),
                account.getAccountOwnerFullName(),
                account.getAccountOwnerEmail(),
                account.getAccountOwnerBirthDate(),
                account.getAccountCreatedDate(),
                account.getPortfolios() == null ? null : account.getPortfolios().stream().map(PortfolioMapper::toPortfolioDto).collect(Collectors.toList())
        );
        return accountDto;
    }
    public static Account toAccount(AccountRequestDto accountDto) {
        Account account = new Account(
                accountDto.getAccountOwnerId(),
                accountDto.getAccountOwnerFullName(),
                accountDto.getAccountOwnerEmail(),
                accountDto.getAccountOwnerPassword(),
                accountDto.getAccountOwnerBirthDate(),
                LocalDate.now(),
                Role.USER,
                null
        );
        return account;
    }
}
