package com.ecagri.trading.dto.request;

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
@Schema(name = "Asset Request", description = "Data Transfer Object (DTO) for creating an asset.")

public class AssetRequestDto {
    @Schema(description = "Code of the stock", example = "AKBNK")
    private String stockCode;

    @Schema(description = "The quantity of stock currently in the portfolio.", example = "5.00")
    private BigDecimal quantity;
}
