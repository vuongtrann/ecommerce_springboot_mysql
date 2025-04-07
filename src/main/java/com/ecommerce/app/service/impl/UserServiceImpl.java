package com.ecommerce.app.service.impl;

import com.ecommerce.app.model.entity.User;
import com.ecommerce.app.repository.UserRepositiory;
import com.ecommerce.app.service.UserService;
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

    @Override
    public User findByUsername(String username) {
        return userRepositiory.findByUsername(username);
    }

    @Override
    public User findByVerificationToken(String token) {
        return userRepositiory.findByVerificationToken(token);
    }

    @Override
    public User findByEmail(String email) {
        return userRepositiory.findByEmail(email);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepositiory.existsByUsername(userName);
    }
}
