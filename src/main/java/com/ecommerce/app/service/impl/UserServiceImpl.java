package com.ecommerce.app.service.impl;


import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.response.dto.UserResponse;
import com.ecommerce.app.model.entity.User;
import com.ecommerce.app.model.mapper.UserMapper;
import com.ecommerce.app.repository.UidSequenceRepository;
import com.ecommerce.app.repository.UserRepositiory;
import com.ecommerce.app.service.UserService;

import com.ecommerce.app.utils.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    private final UidSequenceRepository uidSequenceRepository;
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

    private static final AtomicLong UID_COUNTER = new AtomicLong(1);

    @Override
    public Optional<User> getUserByUid(Long uid) {
        return userRepositiory.findByUid(uid);
    }

    @Override
    public UserResponse getUserResponseByUid(Long uid) {
        User user = userRepositiory.findByUid(uid)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return UserMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepositiory.findAll();
        return UserMapper.toResponseList(users);
    }

}
