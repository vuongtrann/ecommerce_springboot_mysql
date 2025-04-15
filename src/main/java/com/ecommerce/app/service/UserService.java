package com.ecommerce.app.service;

import com.ecommerce.app.model.dao.response.dto.UserResponse;
import com.ecommerce.app.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean existsByEmail(String email);

    void save(User user);

    User findByUsername(String username);

    User findByVerificationToken(String token);

    User findByEmail(String email);

    boolean existsByUserName(String userName);

    List<UserResponse> getAllUsers();

    UserResponse getUserResponseByUid(Long uid);
    Optional<User> getUserByUid(Long uid);

}
