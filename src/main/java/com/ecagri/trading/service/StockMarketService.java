package com.ecagri.trading.service;

import com.ecagri.trading.dto.StockDto;

import java.time.LocalDate;
import java.util.List;

public interface StockMarketService {
    List<StockDto> getStockPrices();
    List<StockDto> getLastStockPrices();
}
