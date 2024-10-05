package com.ecagri.trading.controller;

import com.ecagri.trading.dto.request.TransactionRequestDto;
import com.ecagri.trading.dto.response.TransactionResponseDto;
import com.ecagri.trading.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/{portfolio_id}")
@Tag(name = "Transaction Controller", description = "Operations on transactions.")
public class TransactionController {

    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Buy a stock for a specific portfolio.",
                description = "Buy a stock for a specific portfolio by specifying stock code and quantity."
    )
    @PostMapping(path="/buy")
    public ResponseEntity<TransactionResponseDto> buyTransaction(@PathVariable("portfolio_id") Long portfolio_id, @RequestBody TransactionRequestDto transactionDto) {
        return new ResponseEntity<>(transactionService.buy(portfolio_id, transactionDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Sell a stock from a specific portfolio.",
            description = "Sell a stock from a specific portfolio by specifying stock code and quantity."
    )
    @PostMapping(path ="/sell")
    public ResponseEntity<TransactionResponseDto> sellTransaction(@PathVariable("portfolio_id") Long portfolio_id, @RequestBody TransactionRequestDto transactionDto) {
        return new ResponseEntity<>(transactionService.sell(portfolio_id, transactionDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all transactions of a specific portfolio.",
                description = "Gel all transactions of a specific portfolio by specifying portfolio id."
    )
    @GetMapping(path = "/transactions")
    public ResponseEntity<List<TransactionResponseDto>> getTransactions(@PathVariable("portfolio_id") Long portfolio_id) {
        return new ResponseEntity<>(transactionService.getTransactions(portfolio_id), HttpStatus.OK);
    }


}
