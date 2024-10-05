package com.ecagri.trading.mapper;

import com.ecagri.trading.dto.response.AssetResponseDto;
import com.ecagri.trading.dto.request.AssetRequestDto;
import com.ecagri.trading.entity.Asset;

public class AssetMapper {
    public static Asset toAsset(AssetRequestDto AssetDto) {
        Asset Asset = new Asset(
                AssetDto.getStockCode(),
                AssetDto.getQuantity()
        );
        return Asset;
    }

    public static AssetResponseDto toAssetDto(Asset Asset) {
        AssetResponseDto AssetDto = new AssetResponseDto(
                Asset.getAssetId(),
                Asset.getStockCode(),
                Asset.getQuantity(),
                Asset.getAveragePrice()
        );
        return AssetDto;
    }
}
