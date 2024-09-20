package com.ecagri.trading.repository;

import com.ecagri.trading.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountOwnerId(Long accountOwnerId);
}
