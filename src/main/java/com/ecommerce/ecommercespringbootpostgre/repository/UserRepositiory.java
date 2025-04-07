package com.ecommerce.ecommercespringbootpostgre.repository;

import com.ecommerce.ecommercespringbootpostgre.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositiory extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findByVerificationToken(String token);
    User findByEmail(String email);
}
