package com.ecagri.trading.service;

import com.ecagri.trading.dto.request.AccountRequestDto;
import com.ecagri.trading.dto.request.AuthRequestDto;
import com.ecagri.trading.dto.response.AuthResponseDto;

public interface AuthService {
    AuthResponseDto login(AuthRequestDto authRequestDto);
    AuthResponseDto register(AccountRequestDto accountRequestDto);
}
