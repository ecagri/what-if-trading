package com.ecagri.trading;

import com.ecagri.trading.service.StockMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TradingApplication {

	private StockMarketService stockMarketService;

	@Autowired
	public TradingApplication(StockMarketService stockMarketService) {
		this.stockMarketService = stockMarketService;
	}

	public static void main(String[] args) {
		SpringApplication.run(TradingApplication.class, args);
	}

}
