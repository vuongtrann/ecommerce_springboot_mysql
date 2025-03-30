package com.ecommerce.ecommercespringbootmysql.service.impl;

import com.ecommerce.ecommercespringbootmysql.model.entity.User;
import com.ecommerce.ecommercespringbootmysql.repository.UserRepositiory;
import com.ecommerce.ecommercespringbootmysql.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepositiory userRepositiory;

    @Override
    public boolean existsByEmail(String email) {
        return userRepositiory.existsByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepositiory.save(user);
    }
}
