package com.ecagri.trading.entity;

import com.ecagri.trading.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@Entity
public class Account implements UserDetails {
    @Id
    private Long accountOwnerId;

    private String accountOwnerFullName;

    private String accountOwnerEmail;

    private String accountOwnerPassword;

    private LocalDate accountOwnerBirthDate;

    private LocalDate accountCreatedDate;

    private Role role;

    @OneToMany(mappedBy = "account")
    private List<Portfolio> portfolios;

    public Account(String accountOwnerFullName, Long accountOwnerId) {
        this.accountOwnerFullName = accountOwnerFullName;
        this.accountOwnerId = accountOwnerId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return accountOwnerPassword;
    }

    @Override
    public String getUsername() {
        return accountOwnerId.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
