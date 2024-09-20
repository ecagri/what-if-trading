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
@Table(name="transaction")
public class Transaction {
    @Id
    @GeneratedValue
    private Long transactionId;

    private String stockCode;

    private String transactionType;

    private LocalDateTime transactionDate;

    private BigDecimal stockPrice;

    private BigDecimal stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public Transaction(String stockCode, BigDecimal stockQuantity) {
        this.stockCode = stockCode;
        this.stockQuantity = stockQuantity;
    }
}
