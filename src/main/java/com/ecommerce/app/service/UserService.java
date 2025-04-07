package com.ecommerce.app.service;

import com.ecommerce.app.model.entity.User;

public interface UserService {
    boolean existsByEmail(String email);

    void save(User user);

    User findByUsername(String username);

    User findByVerificationToken(String token);

    User findByEmail(String email);

    boolean existsByUserName(String userName);
}
