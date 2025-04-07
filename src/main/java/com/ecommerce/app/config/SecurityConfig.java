package com.ecommerce.app.config;

import com.ecommerce.app.utils.JWT.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Tắt CSRF vì API REST không cần CSRF
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/v1/auth/**").permitAll() // Endpoint cho đăng nhập, đăng ký
//                        .requestMatchers("/api/v1/category/**").authenticated() // Yêu cầu xác thực cho API category
//                        .requestMatchers("/api/v1/product/**").authenticated() // Yêu cầu xác thực cho API product
//                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Cho phép truy cập Swagger UI và API Docs
                        .anyRequest().permitAll()) // Endpoint còn lại yêu cầu xác thực
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Không dùng session
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Thêm bộ lọc JWT vào chuỗi

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Mã hóa mật khẩu
    }

}
