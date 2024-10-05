package com.ecagri.trading.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Portfolio Request", description = "Data Transfer Object (DTO) for creating a portfolio.")
public class PortfolioRequestDto {

    @Schema(description = "Name of the portfolio", example = "Short-term investment")
    @NotBlank(message = "Portfolio name is required.")
    private String portfolioName;

    @Schema(description = "Available Turkish lira amount in the portfolio", example = "500")
    private BigDecimal balance;

}
