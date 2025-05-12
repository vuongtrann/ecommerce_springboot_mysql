package com.ecommerce.app.service.impl;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.request.Auth.LoginForm;
import com.ecommerce.app.model.dao.request.Auth.RegisterForm;
import com.ecommerce.app.model.entity.Collection;
import com.ecommerce.app.model.entity.UidSequence;
import com.ecommerce.app.model.entity.User;
import com.ecommerce.app.repository.UidSequenceRepository;
import com.ecommerce.app.repository.UserRepositiory;
import com.ecommerce.app.service.AuthService;
import com.ecommerce.app.service.CloudinaryService;
import com.ecommerce.app.service.MailService;
import com.ecommerce.app.service.UserService;
import com.ecommerce.app.utils.ErrorCode;
import com.ecommerce.app.utils.JWT.JwtUtil;
import com.ecommerce.app.utils.Role;
import com.ecommerce.app.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
//@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    private final CloudinaryService cloudinaryService;
    private final UserService userService;
    private final MailService mailService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepositiory userRepository;

    @Value("${server.url}")
    String serverUrl;

    @Override
    public User register(RegisterForm registerForm, MultipartFile avatar) {
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


        user.setUID(generateUniqueUid());
        user.setMessengerId(java.util.UUID.randomUUID().toString());
        user.setCreatedAt(Instant.now().toEpochMilli());
        user.setUpdatedAt(Instant.now().toEpochMilli());
        userService.save(user);

        if (avatar != null && !avatar.isEmpty()) {
            String avatarUrl = cloudinaryService.uploadAvatar(avatar, user.getId());
            user.setAvatar(avatarUrl);
            userService.save(user);
        }

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

    private Long generateUniqueUid() {
        Long uid;
        do {
            uid = generateRandomUid(); // hoặc tăng dần tùy bạn
        } while (userRepository.existsByUID(uid));
        return uid;
    }

    private Long generateRandomUid() {
        // Ví dụ: tạo số ngẫu nhiên trong khoảng 100000 đến 999999
        return ThreadLocalRandom.current().nextLong(100000, 1000000);
    }
}
