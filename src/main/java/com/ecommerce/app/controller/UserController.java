package com.ecommerce.app.controller;

import com.ecommerce.app.model.dao.response.dto.UserResponse;
import com.ecommerce.app.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/uid/{uid}")
    public ResponseEntity<UserResponse> getUserByUid(@PathVariable Long uid) {
        UserResponse userResponse = userService.getUserResponseByUid(uid);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/avatar/{uid}")
    public ResponseEntity<UserResponse> updateAvatar(
            @PathVariable Long uid,
            @RequestParam("avatar") MultipartFile avatar
    ) {
        UserResponse response = userService.updateAvatar(uid, avatar);
        return ResponseEntity.ok(response);
    }
}
