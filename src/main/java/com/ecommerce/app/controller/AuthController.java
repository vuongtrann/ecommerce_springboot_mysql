package com.ecommerce.app.controller;

import com.ecommerce.app.model.dao.request.Auth.ChangePasswordForm;
import com.ecommerce.app.model.dao.request.Auth.ChangeUserNameForm;
import com.ecommerce.app.model.dao.request.Auth.LoginForm;
import com.ecommerce.app.model.dao.request.Auth.RegisterForm;
import com.ecommerce.app.model.dao.response.AppResponse;
import com.ecommerce.app.model.entity.User;
import com.ecommerce.app.service.AuthService;
import com.ecommerce.app.service.UserService;
import com.ecommerce.app.utils.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authservice;
    UserService userservice;

//    @PostMapping("/register")
//    public ResponseEntity<AppResponse<User>> register(@RequestBody @Valid RegisterForm form) {
//        User user = authservice.register(form);
//        return ResponseEntity.ok( AppResponse.builderResponse(
//                SuccessCode.REGISTER, user
//        ));
//    }

    @PostMapping("/register")
    public ResponseEntity<AppResponse<User>> register(
            @ModelAttribute RegisterForm form,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar
    ) {
        User user = authservice.register(form, avatar);
        return ResponseEntity.ok(AppResponse.builderResponse(SuccessCode.REGISTER, user));
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
    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<AppResponse<String>> forgotPassword(@PathVariable String email) {
        authservice.forgotPassword(email);
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.FORGOT_PASSWORD, null
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
