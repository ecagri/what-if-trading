package com.ecagri.trading.service;

import com.ecagri.trading.dto.request.PortfolioRequestDto;
import com.ecagri.trading.dto.response.PortfolioResponseDto;

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
