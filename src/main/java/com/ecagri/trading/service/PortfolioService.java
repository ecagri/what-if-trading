package com.ecagri.trading.service;

import com.ecagri.trading.dto.PortfolioRequestDto;
import com.ecagri.trading.dto.PortfolioResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface PortfolioService {
    List<PortfolioResponseDto> getPortfolios(Long accountId);
    List<PortfolioResponseDto> getPortfolios();
    PortfolioResponseDto getPortfolio(Long portfolioId);
    PortfolioResponseDto createPortfolio(Long accountId, PortfolioRequestDto portfolio);
    PortfolioResponseDto updatePortfolio(Long portfolioId, String portfolioName, BigDecimal balance);
    void deletePortfolio(Long portfolioId);

}
