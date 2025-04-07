package com.ecommerce.app.model.dao.request.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeUserNameForm {
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Username is required")
    private String userName;
}
