package com.ecagri.trading.dto.response;

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
@Schema(name = "Asset Response", description = "Data Transfer Object (DTO) for retrieving asset details.")
public class AssetResponseDto {
    @Schema(description = "Asset ID.", example = "123456")
    private Long assetId;

    @Schema(description = "Code of the stock", example = "AKBNK")
    private String stockCode;

    @Schema(description = "The quantity of stock currently in the portfolio.", example = "5.00")
    private BigDecimal quantity;

    @Schema(description = "The average price at which the stock was purchased.", example = "49.23")
    private BigDecimal averagePrice;
}
