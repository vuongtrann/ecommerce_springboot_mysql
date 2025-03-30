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
import com.ecommerce.ecommercespringbootmysql.utils.Status;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
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

        String confirmationUrl = serverUrl + "/api/v1/auth/verify-email?token=" + token;
        mailService.sendRegistrationConfirmMail(registerForm.getEmail(), confirmationUrl, registerForm.getFirstName(), registerForm.getLastName());
        return user;
    }

    @Override
    public Map<String, String> login(LoginForm loginForm) {
        User user = userService.findByUsername(loginForm.getUsername());
        if (user == null || !passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }
        if(!user.isEnabled()) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_VERIFIED);
        }

        String token = "Bearer " + jwtUtil.generateToken(user.getEmail());
        Map<String,String> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", String.valueOf(user.getId()));
        response.put("role", String.valueOf(user.getRole()));
        return response;
    }

    @Override
    public void verifyEmail(String token) {
        User user = userService.findByVerificationToken(token);
        if (user == null) {
            throw new AppException(ErrorCode.INVALID_VERIFICATION_TOKEN);
        }

        user.setEnabled(true);
        user.setVerificationToken(null);
        user.setStatus(Status.ACTIVE);
        userService.save(user);
    }

    @Override
    public void resendVerifyEmail(String email) {

    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);
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
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        String randomPassword = java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        user.setPassword(passwordEncoder.encode(randomPassword));
        userService.save(user);

        String subject = "Password Reset";
        String body = String.format("Hi %s,\n\nYour new password is: %s\n\nPlease change your password after login.",
                user.getEmail(), randomPassword);
        mailService.sendMail(user.getEmail(), subject, body);
    }

    @Override
    public void resetPassword(String token, String newPassword) {

    }

    @Override
    public void changeUserName(String email, String newUserName) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        user.setUsername(newUserName);
        userService.save(user);

        String subject = "Username Changed Successfully";
        String body = String.format("Hi %s,\n\nYour username has been successfully changed to %s.\n\nIf you did not request this change, please contact support immediately.",
                user.getEmail(), newUserName);
        mailService.sendMail(user.getEmail(), subject, body);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userService.existsByEmail(email);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userService.existsByUserName(userName);
    }

}
