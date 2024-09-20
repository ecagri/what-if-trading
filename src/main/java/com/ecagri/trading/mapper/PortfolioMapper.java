package com.ecagri.trading.mapper;

import com.ecagri.trading.dto.PortfolioRequestDto;
import com.ecagri.trading.dto.PortfolioResponseDto;
import com.ecagri.trading.entity.Portfolio;

import java.math.BigDecimal;
import java.util.stream.Collectors;

public class PortfolioMapper {
    public static PortfolioResponseDto toPortfolioDto(Portfolio portfolio) {
        PortfolioResponseDto portfolioDto = new PortfolioResponseDto(
                portfolio.getPortfolioId(),
                portfolio.getPortfolioName(),
                portfolio.getBalance(),
                portfolio.getAssets() == null ? null: portfolio.getAssets().stream().map(AssetMapper::toAssetDto).collect(Collectors.toList()),
                portfolio.getTransactions() == null ? null : portfolio.getTransactions().stream().map(TransactionMapper::toTransactionDto).collect(Collectors.toList())
        );
        return portfolioDto;
    }
    public static Portfolio toPortfolio(PortfolioRequestDto portfolioDto) {
        Portfolio portfolio = new Portfolio(
                portfolioDto.getPortfolioName(),
                portfolioDto.getBalance() == null ? BigDecimal.ZERO : portfolioDto.getBalance()
        );
        return portfolio;
    }
}
