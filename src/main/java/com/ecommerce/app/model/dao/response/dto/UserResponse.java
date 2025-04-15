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
    private Long id;
    private Long uid;
    private String firstName;
    private String lastName;
    private String email;
    private String phone; private
    Status status; }
