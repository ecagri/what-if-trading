package com.ecagri.trading.controller;

import com.ecagri.trading.dto.StockDto;
import com.ecagri.trading.service.StockMarketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock_market")
@Tag(name = "Stock Market Controller", description = "Operations on stock market.")
public class StockMarketController {

    private StockMarketService stockMarketService;

    @Autowired
    StockMarketController(StockMarketService stockMarketService) {
        this.stockMarketService = stockMarketService;
    }

    @Operation(summary = "Retrieve historical stock prices",
            description = "Retrieve the complete historical data for stocks."
    )
    @GetMapping
    public ResponseEntity<List<StockDto>> getStockPrices() {
        return new ResponseEntity<>(stockMarketService.getStockPrices(), HttpStatus.OK);
    }
    @Operation(summary = "Retrieve current day stock prices",
            description = "Retrieve the complete data for stocks for the current day."
    )
    @GetMapping("/last")
    public ResponseEntity<List<StockDto>> getLastStockPrices() {
        return new ResponseEntity<>(stockMarketService.getStockPrices(), HttpStatus.OK);
    }

}
