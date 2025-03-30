package com.ecommerce.ecommercespringbootmysql.service;

import com.ecommerce.ecommercespringbootmysql.model.entity.User;

public interface UserService {
    boolean existsByEmail(String email);

    void save(User user);
}
