package com.ecagri.trading.service;

import com.ecagri.trading.dto.request.AccountRequestDto;
import com.ecagri.trading.dto.response.AccountResponseDto;

import java.util.List;

public interface AccountService {
    AccountResponseDto createAccount(AccountRequestDto accountDto);
    List<AccountResponseDto> getAllAccounts();
    AccountResponseDto getAccount(Long customer_id);
    void deleteAccount(Long customer_id);
    AccountResponseDto updateAccount(Long customer_id, String account_owner_id);
}
