package com.ecommerce.app.repository;

import com.ecommerce.app.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository <RefreshToken, String>{
    Optional<RefreshToken> findByToken(String token);
//    List<RefreshToken> findAllByUserId(Long userId);
//    void deleteByUserId(Long userId);
}
