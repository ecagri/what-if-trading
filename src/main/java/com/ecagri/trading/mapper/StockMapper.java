package com.ecagri.trading.mapper;

import com.ecagri.trading.dto.StockDto;
import com.ecagri.trading.entity.HistoricalStock;
import com.ecagri.trading.entity.Stock;

public class StockMapper {
    public static HistoricalStock toHistoricalStock(Stock stock) {
        HistoricalStock historicalStock = new HistoricalStock(
                stock.getStockId(),
                stock.getStockCode(),
                stock.getName(),
                stock.getPrice(),
                stock.getOpening(),
                stock.getPreviousClosing(),
                stock.getHighest(),
                stock.getLowest(),
                stock.getChangePercentage(),
                stock.getDate()
        );
        return historicalStock;
    }

    public static StockDto toStockDto(HistoricalStock historicalStock) {
        StockDto stockDto = new StockDto(
                historicalStock.getStockId(),
                historicalStock.getStockCode(),
                historicalStock.getName(),
                historicalStock.getPrice(),
                historicalStock.getOpening(),
                historicalStock.getPreviousClosing(),
                historicalStock.getHighest(),
                historicalStock.getLowest(),
                historicalStock.getChangePercentage(),
                historicalStock.getDate()
        );
        return stockDto;
    }

    public static StockDto toStockDto(Stock stock){
        return StockMapper.toStockDto(StockMapper.toHistoricalStock(stock));
    }
}
