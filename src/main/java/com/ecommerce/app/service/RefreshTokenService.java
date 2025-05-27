package com.ecommerce.app.service;

import com.ecommerce.app.model.entity.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String email);
    RefreshToken verifyExpiration(RefreshToken token);
}
