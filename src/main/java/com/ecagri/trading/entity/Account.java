package com.ecagri.trading.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;

    private String accountOwnerFullName;

    @Column(unique = true)
    private Long accountOwnerId;

    @OneToMany(mappedBy = "account")
    private List<Portfolio> portfolios;

    public Account(String accountOwnerFullName, Long accountOwnerId) {
        this.accountOwnerFullName = accountOwnerFullName;
        this.accountOwnerId = accountOwnerId;
    }
}
