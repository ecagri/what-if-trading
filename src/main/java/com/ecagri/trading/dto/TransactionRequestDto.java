package com.ecagri.trading.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Transaction Request", description = "Data Transfer Object (DTO) for creating a transaction.")

public class TransactionRequestDto {


    @Schema(description = "Code of the stock", example = "AKBNK")
    private String stockCode;

    @Schema(description = "The amount of stock transacted.", example = "5.00")
    private BigDecimal stockQuantity;
}
