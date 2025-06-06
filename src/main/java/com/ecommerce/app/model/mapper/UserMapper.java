package com.ecommerce.app.model.mapper;

import com.ecommerce.app.model.dao.response.dto.UserResponse;
import com.ecommerce.app.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .UID(user.getUID())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatarUrl(user.getAvatar() != null ? user.getAvatar() : null) // hoặc ẩn nếu muốn
                .email(user.getEmail())
                .phone(user.getPhone())
                .status(user.getStatus())
                .build();
    }
    public static List<UserResponse> toResponseList(List<User> users) {
        return users.stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }
}
