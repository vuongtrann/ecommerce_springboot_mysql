package com.ecommerce.app.repository;

import com.ecommerce.app.model.dao.response.dto.UserResponse;
import com.ecommerce.app.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositiory extends JpaRepository<User, String> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findByVerificationToken(String token);
    User findByEmail(String email);
    Optional<User> findByUID(Long uid);
    boolean existsByUID(Long uid);
}
