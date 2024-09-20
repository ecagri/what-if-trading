package com.ecagri.trading.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="historical_data")
public class HistoricalStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    private String stockCode;

    private String name;

    private BigDecimal price;

    private BigDecimal opening;

    private BigDecimal previousClosing;

    private BigDecimal highest;

    private BigDecimal lowest;

    private BigDecimal changePercentage;

    private LocalDateTime date;

}
