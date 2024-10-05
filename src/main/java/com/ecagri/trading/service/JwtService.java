package com.ecagri.trading.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String extractUser(String jwt);
    String generateToken(UserDetails userDetails);
    String generateToken(Map<String, Object> claims, UserDetails userDetails);
    boolean isTokenValid(String jwt, UserDetails userDetails);
    boolean isTokenExpired(String jwt);
}
