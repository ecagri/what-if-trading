package com.ecagri.trading.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Transaction Response", description = "Data Transfer Object (DTO) for retrieving transaction details.")
public class TransactionResponseDto {

    @Schema(description = "Transaction ID.", example = "123456")
    private Long transactionId;

    @Schema(description = "Code of the stock.", example = "AKBNK")
    private String stockCode;

    @Schema(description = "The type of the transaction.", example = "BUY")
    private String transactionType;

    @Schema(description = "The date the transaction was occurred.", example = "2024-09-18T15:35:45")
    private LocalDateTime transactionDate;

    @Schema(description = "Price of the stock.", example = "150.25")
    private BigDecimal stockPrice;

    @Schema(description = "The amount of stock transacted.", example = "5.00")
    private BigDecimal stockQuantity;
}
