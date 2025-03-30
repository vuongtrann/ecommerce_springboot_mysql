package com.ecommerce.ecommercespringbootmysql.service.impl;

import com.ecommerce.ecommercespringbootmysql.exception.AppException;
import com.ecommerce.ecommercespringbootmysql.model.dao.request.Auth.LoginForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.request.Auth.RegisterForm;
import com.ecommerce.ecommercespringbootmysql.model.entity.User;
import com.ecommerce.ecommercespringbootmysql.service.AuthService;
import com.ecommerce.ecommercespringbootmysql.service.MailService;
import com.ecommerce.ecommercespringbootmysql.service.UserService;
import com.ecommerce.ecommercespringbootmysql.utils.ErrorCode;
import com.ecommerce.ecommercespringbootmysql.utils.JWT.JwtUtil;
import com.ecommerce.ecommercespringbootmysql.utils.Role;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
@Service
@RequiredArgsConstructor
//@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final MailService mailService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${server.url}")
    String serverUrl;

    @Override
    public User register(RegisterForm registerForm) {
        if (userService.existsByEmail(registerForm.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String hashedPassword = passwordEncoder.encode(registerForm.getPassword());
        String token = generateVerificationToken(registerForm.getEmail());

        User user = new User(
                registerForm.getFirstName(),
                registerForm.getLastName(),
                registerForm.getPhone(),
                registerForm.getEmail(),
                registerForm.getEmail(), // username = email
                hashedPassword,
                false,
                token,
                Role.USER
        );
        user.setCreatedAt(Instant.now().toEpochMilli());
        user.setUpdatedAt(Instant.now().toEpochMilli());
        userService.save(user);

        String confirmationUrl = serverUrl + "/api/auth/verify-email?token=" + token;
        mailService.sendRegistrationConfirmMail(registerForm.getEmail(), confirmationUrl, registerForm.getFirstName(), registerForm.getLastName());
        return user;
    }

    @Override
    public Map<String, String> login(LoginForm loginForm) {
        return Map.of();
    }

    @Override
    public void verifyEmail(String token) {

    }

    @Override
    public void resendVerifyEmail(String email) {

    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {

    }

    @Override
    public String generateVerificationToken(String email) {
        return java.util.UUID.randomUUID().toString();
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public void forgotPassword(String email) {

    }

    @Override
    public void resetPassword(String token, String newPassword) {

    }

    @Override
    public void changeUserName(String email, String newUserName) {

    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean existsByUserName(String userName) {
        return false;
    }
}
