package com.ecommerce.ecommercespringbootmysql.repository;

import com.ecommerce.ecommercespringbootmysql.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositiory extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
