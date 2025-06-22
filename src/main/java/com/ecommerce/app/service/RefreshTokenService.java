package com.ecommerce.app.service;

import com.ecommerce.app.model.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String email);
    RefreshToken verifyExpiration(RefreshToken token);
    Optional<RefreshToken> findByToken(String token);
    void deleteRefreshToken(RefreshToken token);
}
