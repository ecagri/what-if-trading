package com.ecagri.trading.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Stock", description = "Data Transfer Object (DTO) for retrieving stock details.")
public class StockDto {

    @Schema(description = "Stock ID.", example = "1001")
    private Long stockId;

    @Schema(description = "Code of the stock.", example = "AKBNK")
    private String stockCode;

    @Schema(description = "Name of the stock.", example = "Akbank A.Ş.")
    private String name;

    @Schema(description = "Current price of the stock.", example = "150.25")
    private BigDecimal price;

    @Schema(description = "Opening price of the stock.", example = "148.50")
    private BigDecimal opening;

    @Schema(description = "Previous day's closing price of the stock.", example = "149.80")
    private BigDecimal previousClosing;

    @Schema(description = "Highest price of the stock during the day.", example = "151.00")
    private BigDecimal highest;

    @Schema(description = "Lowest price of the stock during the day.", example = "147.80")
    private BigDecimal lowest;

    @Schema(description = "Percentage change in stock price during the day.", example = "0.45")
    private BigDecimal changePercentage;

    @Schema(description = "Date and time of the stock data", example = "2024-09-18T15:35:45")
    private LocalDateTime date;

}


