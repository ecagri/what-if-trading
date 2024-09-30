package com.ecagri.trading.service.impl;

import com.ecagri.trading.dto.StockDto;
import com.ecagri.trading.entity.Stock;
import com.ecagri.trading.mapper.StockMapper;
import com.ecagri.trading.repository.HistoricalStockRepository;
import com.ecagri.trading.repository.StockRepository;
import com.ecagri.trading.service.StockMarketService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockMarketServiceImpl implements StockMarketService {

    private RestTemplate restTemplate;

    private StockRepository stockRepository;

    private HistoricalStockRepository historicalStockRepository;

    @Value("${stock.api.key}")
    private String stockApiKey;

    @Autowired
    public StockMarketServiceImpl(RestTemplate restTemplate, StockRepository stockRepository, HistoricalStockRepository historicalStockRepository) {
        this.restTemplate = restTemplate;
        this.stockRepository = stockRepository;
        this.historicalStockRepository = historicalStockRepository;
    }

    @Override
    public List<StockDto> getStockPrices(){
        return historicalStockRepository.findAll().stream().map(StockMapper::toStockDto).collect(Collectors.toList());
    }

    public List<StockDto> getLastStockPrices(){
        return stockRepository.findAll().stream().map(StockMapper::toStockDto).collect(Collectors.toList());
    }

    @Transactional
    @Scheduled(cron = "0 30 18 * * MON-FRI")
    protected void pullStockPrices() {
        String url = "https://financialmodelingprep.com/api/v3/symbol/Ist?apikey=" + stockApiKey;
        try {
            List<Stock> stocks = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Stock>>() {}
            ).getBody();

            if(stocks != null && !stocks.isEmpty()) {

                stockRepository.deleteAll();

                stockRepository.saveAll(stocks);

                historicalStockRepository.saveAll(
                        stockRepository.findAll().stream()
                                .map(StockMapper::toHistoricalStock)
                                .collect(Collectors.toList())
                );
            } else {
                System.err.println("API returned no stock data.");
            }

        } catch (Exception e) {
            System.err.println("Error while updating stocks: " + e.getMessage());
        }
    }

}
