package com.ecagri.trading.mapper;

import com.ecagri.trading.dto.TransactionRequestDto;
import com.ecagri.trading.dto.TransactionResponseDto;
import com.ecagri.trading.entity.Transaction;

public class TransactionMapper {
    public static Transaction toTransaction(TransactionRequestDto transactionDto){
        Transaction transaction = new Transaction(
                transactionDto.getStockCode(),
                transactionDto.getStockQuantity()
        );
        return transaction;
    }

    public static TransactionResponseDto toTransactionDto(Transaction transaction){
        TransactionResponseDto transactionDto = new TransactionResponseDto(
                transaction.getTransactionId(),
                transaction.getStockCode(),
                transaction.getTransactionType(),
                transaction.getTransactionDate(),
                transaction.getStockPrice(),
                transaction.getStockQuantity()
        );
        return transactionDto;
    }
}
