package com.ecommerce.app.model.dao.response.dto;

import com.ecommerce.app.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private Long UID;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private String email;
    private String phone;
    Status status; }
