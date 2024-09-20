package com.ecagri.trading.service.impl;

import com.ecagri.trading.dto.AssetRequestDto;
import com.ecagri.trading.dto.AssetResponseDto;
import com.ecagri.trading.entity.Portfolio;
import com.ecagri.trading.entity.Asset;
import com.ecagri.trading.entity.Stock;
import com.ecagri.trading.mapper.AssetMapper;
import com.ecagri.trading.repository.AssetRepository;
import com.ecagri.trading.repository.PortfolioRepository;
import com.ecagri.trading.repository.StockRepository;
import com.ecagri.trading.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssetServiceImpl implements AssetService {

    PortfolioRepository portfolioRepository;

    AssetRepository assetRepository;

    StockRepository stockRepository;

    @Autowired
    public AssetServiceImpl(PortfolioRepository portfolioRepository, AssetRepository assetRepository, StockRepository stockRepository) {
        this.portfolioRepository = portfolioRepository;
        this.assetRepository = assetRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public AssetResponseDto createAsset(Long portfolioId, AssetRequestDto AssetDto) {
        Asset asset = AssetMapper.toAsset(AssetDto);

        Optional<Stock> stock = stockRepository.findByStockCode(asset.getStockCode());

        if(stock.isEmpty()){
            throw new RuntimeException("Stock not found");
        }

        asset.setAveragePrice(stock.get().getPrice());

        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);

        if(portfolio.isEmpty()) {
            throw new IllegalArgumentException("Portfolio not exist.");
        }

        asset.setPortfolio(portfolio.get());

        Asset savedAsset = assetRepository.save(asset);

        return AssetMapper.toAssetDto(savedAsset);

    }

    @Override
    public List<AssetResponseDto> getAssetsByPortfolio(Long portfolioId){
        return assetRepository.findByPortfolioPortfolioId(portfolioId).stream().map(AssetMapper::toAssetDto).collect(Collectors.toList());
    }

    @Override
    public List<AssetResponseDto> getAssetsByCode(String stockCode) {
        return assetRepository.findByStockCode(stockCode).stream().map(AssetMapper::toAssetDto).collect(Collectors.toList());
    }

    @Override
    public List<AssetResponseDto> getAllAssets() {
        return assetRepository.findAll().stream().map(AssetMapper::toAssetDto).collect(Collectors.toList());
    }
}
