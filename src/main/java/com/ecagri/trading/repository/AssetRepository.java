package com.ecagri.trading.repository;

import com.ecagri.trading.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByStockCode(String stockCode);

    List<Asset> findByPortfolioPortfolioId(Long portfolioId);

    Optional<Asset> findByStockCodeAndPortfolioPortfolioId(String stockCode, Long portfolioId);
}
