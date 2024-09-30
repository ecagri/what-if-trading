package com.ecagri.trading.service.impl;

import com.ecagri.trading.dto.AssetRequestDto;
import com.ecagri.trading.dto.AssetResponseDto;
import com.ecagri.trading.entity.Asset;
import com.ecagri.trading.entity.Portfolio;
import com.ecagri.trading.entity.Stock;
import com.ecagri.trading.repository.AssetRepository;
import com.ecagri.trading.repository.PortfolioRepository;
import com.ecagri.trading.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AssetServiceImplTest {

    @InjectMocks
    private AssetServiceImpl assetService;

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private AssetRepository assetRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAsset_shouldThrowExceptionWhenStockNotFound(){

        AssetRequestDto assetRequestDto = new AssetRequestDto();

        when(stockRepository.findByStockCode("")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> assetService.createAsset(1L, assetRequestDto));
    }

    @Test
    void createAsset_shouldThrowExceptionWhenPortfolioNotFound(){
        AssetRequestDto assetRequestDto = new AssetRequestDto("AKBNK", BigDecimal.valueOf(8));

        when(stockRepository.findByStockCode("AKBNK")).thenReturn(Optional.of(new Stock()));

        when(portfolioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> assetService.createAsset(1L, assetRequestDto));
    }

    @Test
    void createAsset_shouldCreateAsset(){
        AssetRequestDto assetRequestDto = new AssetRequestDto("AKBNK", BigDecimal.valueOf(8));

        when(stockRepository.findByStockCode("AKBNK")).thenReturn(Optional.of(new Stock()));

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(new Portfolio()));

        when(assetRepository.save(any(Asset.class))).thenReturn(new Asset());

        assetService.createAsset(1L, assetRequestDto);

        verify(assetRepository).save(any(Asset.class));
    }

    @Test
    void getAssetsByPortfolio_shouldReturnListOfAssets(){
        Asset asset1 = new Asset();
        Asset asset2 = new Asset();
        Asset asset3 = new Asset();

        when(assetRepository.findByPortfolioPortfolioId(1L)).thenReturn(List.of(asset1, asset2, asset3));

        List<AssetResponseDto> result = assetService.getAssetsByPortfolio(1L);

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(assetRepository, times(1)).findByPortfolioPortfolioId(1L);

    }

    @Test
    void getAssetsByStockCode_shouldReturnListOfAssets(){
        Asset asset1 = new Asset();
        Asset asset2 = new Asset();
        Asset asset3 = new Asset();

        when(assetRepository.findByStockCode("AKBNK")).thenReturn(List.of(asset1, asset2, asset3));

        List<AssetResponseDto> result = assetService.getAssetsByCode("AKBNK");

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(assetRepository, times(1)).findByStockCode("AKBNK");
    }

    @Test
    void getAssets_shouldReturnListOfAssets(){
        Asset asset1 = new Asset();
        Asset asset2 = new Asset();
        Asset asset3 = new Asset();

        when(assetRepository.findAll()).thenReturn(List.of(asset1, asset2, asset3));

        List<AssetResponseDto> result = assetService.getAllAssets();

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(assetRepository, times(1)).findAll();
    }
}
