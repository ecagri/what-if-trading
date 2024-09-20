package com.ecagri.trading.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    private String stockCode;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("open")
    private BigDecimal opening;

    @JsonProperty("previousClosing")
    private BigDecimal previousClosing;

    @JsonProperty("dayHigh")
    private BigDecimal highest;

    @JsonProperty("dayLow")
    private BigDecimal lowest;

    @JsonProperty("changePercentage")
    private BigDecimal changePercentage;

    private LocalDateTime date;

    @JsonProperty("timestamp")
    @JsonSetter("timestamp")
    public void setDate(long timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        this.date = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @JsonProperty("symbol")
    @JsonSetter("symbol")
    public void setSymbol(String symbol) {
        this.stockCode = symbol.replace(".IS", "");
    }
}
