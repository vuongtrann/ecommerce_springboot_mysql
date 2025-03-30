package com.ecommerce.ecommercespringbootmysql.controller;

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
}
