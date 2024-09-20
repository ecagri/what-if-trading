package com.ecagri.trading.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Portfolio Response", description = "Data Transfer Object (DTO) for retrieving portfolio details.")
public class PortfolioResponseDto {
    @Schema(description = "Portfolio ID.", example = "123456")
    private Long portfolioId;

    @Schema(description = "Name of the portfolio", example = "Short-term investment")
    private String portfolioName;

    @Schema(description = "Available Turkish lira amount in the portfolio", example = "500")
    private BigDecimal balance;

    @Schema(description = "A list of assets included in the portfolio.")
    private List<AssetResponseDto> assets;

    @Schema(description = "A list of all transactions associated with the portfolio.")
    private List<TransactionResponseDto> transactions;

}
