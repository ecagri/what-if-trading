package com.ecagri.trading.repository;

import com.ecagri.trading.entity.HistoricalStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalStockRepository extends JpaRepository<HistoricalStock, Long> {
}
