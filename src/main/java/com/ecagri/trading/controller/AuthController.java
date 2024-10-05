package com.ecagri.trading.controller;

import com.ecagri.trading.dto.request.AccountRequestDto;
import com.ecagri.trading.dto.request.AuthRequestDto;
import com.ecagri.trading.dto.response.AuthResponseDto;
import com.ecagri.trading.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto){
        return new ResponseEntity<>(authService.login(authRequestDto), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody AccountRequestDto accountRequestDto){
        return new ResponseEntity<>(authService.register(accountRequestDto), HttpStatus.OK);
    }

}
