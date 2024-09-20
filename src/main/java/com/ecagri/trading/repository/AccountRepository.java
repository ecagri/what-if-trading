package com.ecagri.trading.repository;

import com.ecagri.trading.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountOwnerId(Long accountOwnerId);
}
