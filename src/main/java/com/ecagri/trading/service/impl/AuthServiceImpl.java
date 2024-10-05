package com.ecagri.trading.service.impl;

import com.ecagri.trading.dto.request.AccountRequestDto;
import com.ecagri.trading.dto.request.AuthRequestDto;
import com.ecagri.trading.dto.response.AuthResponseDto;
import com.ecagri.trading.entity.Account;
import com.ecagri.trading.mapper.AccountMapper;
import com.ecagri.trading.repository.AccountRepository;
import com.ecagri.trading.service.AuthService;
import com.ecagri.trading.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequestDto.getAccountOwnerId(),
                            authRequestDto.getAccountOwnerPassword()
                    )
            );

            Account account = accountRepository.findByAccountOwnerId(authRequestDto.getAccountOwnerId())
                    .orElseThrow();


            String jwt = jwtService.generateToken(account);

            return new AuthResponseDto(jwt);
        }catch (Exception e){
            throw new RuntimeException("Invalid username or password");
        }
    }

    public AuthResponseDto register(AccountRequestDto accountDto) {
        if(accountRepository.findByAccountOwnerId(accountDto.getAccountOwnerId()).isPresent()){
            throw new RuntimeException("The account already exists.");
        }

        Account account = AccountMapper.toAccount(accountDto);

        account.setAccountOwnerPassword(passwordEncoder.encode(accountDto.getAccountOwnerPassword()));

        Account savedAccount = accountRepository.save(account);

        String jwt = jwtService.generateToken(savedAccount);

        return new AuthResponseDto(jwt);

    }
}
