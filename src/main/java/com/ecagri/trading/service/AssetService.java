package com.ecagri.trading.service;

import com.ecagri.trading.dto.AssetRequestDto;
import com.ecagri.trading.dto.AssetResponseDto;

import java.util.List;

public interface AssetService {

    AssetResponseDto createAsset(Long portfolioId, AssetRequestDto AssetDto);

    List<AssetResponseDto> getAssetsByPortfolio(Long portfolioId);

    List<AssetResponseDto> getAssetsByCode(String AssetCode);

    List<AssetResponseDto> getAllAssets();
}
