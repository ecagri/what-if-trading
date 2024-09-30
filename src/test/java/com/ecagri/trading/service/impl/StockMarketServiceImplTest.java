package com.ecagri.trading.service.impl;

import com.ecagri.trading.dto.StockDto;
import com.ecagri.trading.entity.HistoricalStock;
import com.ecagri.trading.entity.Stock;
import com.ecagri.trading.repository.HistoricalStockRepository;
import com.ecagri.trading.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockMarketServiceImplTest {

    @InjectMocks
    private StockMarketServiceImpl stockMarketServiceImpl;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private HistoricalStockRepository historicalStockRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStockPrices_shouldReturnListOfStocks(){
        HistoricalStock stock1 = new HistoricalStock();
        HistoricalStock stock2 = new HistoricalStock();
        HistoricalStock stock3 = new HistoricalStock();

        when(historicalStockRepository.findAll()).thenReturn(List.of(stock1, stock2, stock3));

        List<StockDto> stocks = stockMarketServiceImpl.getStockPrices();

        assertNotNull(stocks);
        assertEquals(3, stocks.size());
        verify(historicalStockRepository, times(1)).findAll();
    }

    @Test
    void getLastStockPrices_shouldReturnListOfStocks(){
        Stock stock1 = new Stock();
        Stock stock2 = new Stock();
        Stock stock3 = new Stock();

        when(stockRepository.findAll()).thenReturn(List.of(stock1, stock2, stock3));

        List<StockDto> stocks = stockMarketServiceImpl.getLastStockPrices();

        assertNotNull(stocks);
        assertEquals(3, stocks.size());
        verify(stockRepository, times(1)).findAll();

    }
}