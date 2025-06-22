package com.ecommerce.app.service.impl;

import com.ecommerce.app.model.entity.RefreshToken;
import com.ecommerce.app.model.entity.User;
import com.ecommerce.app.repository.RefreshTokenRepository;
import com.ecommerce.app.repository.UserRepositiory;
import com.ecommerce.app.service.RefreshTokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepositiory userRepositiory;

    @Value("${jwt.refreshToken.expiration}")
    private Long refreshTokenExpiration;

    @Override
    public RefreshToken createRefreshToken(String email) {
        User user = userRepositiory.findByEmail(email);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration).toEpochMilli());
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate() < Instant.now().toEpochMilli()) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired.");
        }
        return token;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public void deleteRefreshToken(RefreshToken token) {

    }
}
