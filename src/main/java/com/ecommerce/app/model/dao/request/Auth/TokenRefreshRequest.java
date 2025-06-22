package com.ecommerce.app.model.dao.request.Auth;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
