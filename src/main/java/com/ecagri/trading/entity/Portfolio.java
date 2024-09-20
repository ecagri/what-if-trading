package com.ecagri.trading.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "portfolio")
@Entity
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long portfolioId;

    private String portfolioName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private BigDecimal balance;

    @OneToMany(mappedBy = "portfolio")
    private List<Asset> assets;

    @OneToMany(mappedBy = "portfolio")
    private List<Transaction> transactions;

    public Portfolio(String portfolioName, BigDecimal balance) {
        this.portfolioName = portfolioName;
        this.balance = balance;
    }
}
