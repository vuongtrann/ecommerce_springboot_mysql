package com.ecommerce.ecommercespringbootmysql.service;

import com.ecommerce.ecommercespringbootmysql.model.dao.request.Auth.LoginForm;
import com.ecommerce.ecommercespringbootmysql.model.dao.request.Auth.RegisterForm;
import com.ecommerce.ecommercespringbootmysql.model.entity.User;

import java.util.Map;


public interface AuthService {
    public User register(RegisterForm registerForm);
    public Map<String, String> login(LoginForm loginForm);
    public void verifyEmail(String token);
    public void resendVerifyEmail(String email);
    public void changePassword(String email, String oldPassword, String newPassword);
    public String generateVerificationToken(String email);
    public void logout(String token);
    public void forgotPassword(String email);
    public void resetPassword(String token, String newPassword);
    public void changeUserName(String email, String newUserName);
    public boolean existsByEmail(String email);
    public boolean existsByUserName(String userName);
}
