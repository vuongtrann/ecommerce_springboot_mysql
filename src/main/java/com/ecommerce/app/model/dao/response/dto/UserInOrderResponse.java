package com.ecommerce.app.model.dao.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInOrderResponse {
    private Long UID;
    private String firstName;
    private String lastName;
    private String avatar;
    private String email;
    private String phone;
}
