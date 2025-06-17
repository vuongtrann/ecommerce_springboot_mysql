package com.ecommerce.app.service.impl;


import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.request.UserForm;
import com.ecommerce.app.model.dao.response.dto.UserResponse;
import com.ecommerce.app.model.entity.User;
import com.ecommerce.app.model.mapper.UserMapper;
import com.ecommerce.app.repository.UserRepositiory;
import com.ecommerce.app.service.CloudinaryService;
import com.ecommerce.app.service.UserService;

import com.ecommerce.app.utils.Enum.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepositiory userRepositiory;
    CloudinaryService  cloudinaryService;

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
        return userRepositiory.findByUID(uid);
    }

    @Override
    public UserResponse getUserResponseByUid(Long uid) {
        User user = userRepositiory.findByUID(uid)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return UserMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepositiory.findAll();
        return UserMapper.toResponseList(users);
    }

    @Override
    public UserResponse updateInfo(Long userUid, UserForm userForm){
        User user = userRepositiory.findByUID(userUid).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setPhone(userForm.getPhone());
        userRepositiory.save(user);
        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse updateAvatar(Long uid, MultipartFile avatar) {
        User user = userRepositiory
                .findByUID(uid)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        String avatarUrl = cloudinaryService.uploadAvatar(avatar, user.getId());
        user.setAvatar(avatarUrl);
        userRepositiory.save(user);

        return UserMapper.toResponse(user);
    }


}
