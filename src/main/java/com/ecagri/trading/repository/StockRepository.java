package com.ecagri.trading.repository;

import com.ecagri.trading.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    Optional<Stock> findByStockCode(String stockCode);
}
