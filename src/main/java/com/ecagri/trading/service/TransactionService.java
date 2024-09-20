package com.ecagri.trading.service;

import com.ecagri.trading.dto.TransactionRequestDto;
import com.ecagri.trading.dto.TransactionResponseDto;

import java.util.List;

public interface TransactionService {
    TransactionResponseDto buy(Long portfolioId, TransactionRequestDto transactionDto);
    TransactionResponseDto sell(Long portfolioId, TransactionRequestDto transactionDto);
    List<TransactionResponseDto> getTransactions(Long portfolioId);
}
