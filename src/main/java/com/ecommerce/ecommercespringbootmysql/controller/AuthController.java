package com.ecommerce.ecommercespringbootmysql.controller;

import com.ecommerce.ecommercespringbootmysql.model.dao.request.Auth.ChangePasswordForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.request.Auth.ChangeUserNameForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.request.Auth.LoginForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.request.Auth.RegisterForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.response.AppResponse;
import com.ecommerce.ecommercespringbootmysql.model.entity.User;
import com.ecommerce.ecommercespringbootmysql.service.AuthService;
import com.ecommerce.ecommercespringbootmysql.utils.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authservice;

    @PostMapping("/register")
    public ResponseEntity<AppResponse<User>> register(@RequestBody @Valid RegisterForm form) {
        User user = authservice.register(form);
        return ResponseEntity.ok( AppResponse.builderResponse(
                SuccessCode.REGISTER, user
        ));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<AppResponse<String>> verify(@RequestParam String token) {
        authservice.verifyEmail(token);
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.VERIFY_ACCOUNT, null
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<AppResponse<Map<String,String>>> login(@RequestBody @Valid LoginForm form) {
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.LOGIN, authservice.login(form)
        ));
    }

    @PostMapping("/change-username")
    public ResponseEntity<AppResponse<String>> changeUserName(@RequestBody ChangeUserNameForm form) {
        authservice.changeUserName(form.getEmail(), form.getUserName());
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.CHANGE_USERNAME, null
        ));
    }
    @PostMapping("/change-password")
    public ResponseEntity<AppResponse<String>> changePassword(@RequestBody ChangePasswordForm form) {
        authservice.changePassword(form.getEmail(), form.getOldPassword(), form.getNewPassword());
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.CHANGE_PASSWORD, null
        ));
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<AppResponse<String>> forgotPassword(@RequestParam String email) {
        authservice.forgotPassword(email);
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.FORGOT_PASSWORD, null
        ));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<AppResponse<String>> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        authservice.resetPassword(token, newPassword);
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.RESET_PASSWORD, null
        ));
    }
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(authservice.existsByEmail(email));
    }

    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Boolean> existsByUserName(@PathVariable String username) {
        return ResponseEntity.ok(authservice.existsByUserName(username));
    }

}
