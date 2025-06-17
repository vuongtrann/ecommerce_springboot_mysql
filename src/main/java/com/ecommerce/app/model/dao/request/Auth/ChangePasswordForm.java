package com.ecommerce.app.model.dao.request.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordForm {
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Old password is required")
    private String oldPassword;
    @NotBlank(message = "New password is required")
    private String newPassword;
}
