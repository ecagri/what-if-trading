package com.ecagri.trading.controller;

import com.ecagri.trading.dto.AccountRequestDto;
import com.ecagri.trading.dto.AccountResponseDto;
import com.ecagri.trading.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path ="/api/v1/accounts")
@Tag(name = "Account Controller", description = "Operations on accounts.")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Create an account",
                description = "Create a new account. The response contains created account id, owner information and portfolios."
    )
    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody AccountRequestDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all accounts",
                description = "Get all accounts in the system. The response is all accounts and information."
    )
    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @Operation(summary = "Get an account",
                description = "Access account info and portfolios."
    )
    @GetMapping(path="/{customer_id}")
    public ResponseEntity<AccountResponseDto> getAccount(@PathVariable Long customer_id) {
        return new ResponseEntity<>(accountService.getAccount(customer_id), HttpStatus.OK);
    }

    @DeleteMapping(path="{customer_id}")
    @Operation(summary = "Delete an account",
                description = "Deletes a specific account and its portfolios, assets and transactions from the system."
    )
    public ResponseEntity<String> deleteAccount(@PathVariable Long customer_id) {
        accountService.deleteAccount(customer_id);

        return new ResponseEntity<>("Account is deleted", HttpStatus.OK);
    }

    @PutMapping(path="/{customer_id}")
    @Operation(summary = "Update an accout",
                description = "Updates the owner name of the account."
    )
    public ResponseEntity<AccountResponseDto> updateAccount(@PathVariable Long customer_id, @RequestParam String account_owner_name){
        return new ResponseEntity<>(accountService.updateAccount(customer_id, account_owner_name), HttpStatus.OK);
    }
}
