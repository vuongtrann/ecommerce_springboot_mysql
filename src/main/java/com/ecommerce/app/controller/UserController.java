package com.ecommerce.app.controller;

import com.ecommerce.app.model.dao.request.UserForm;
import com.ecommerce.app.model.dao.response.AppResponse;
import com.ecommerce.app.model.dao.response.dto.UserResponse;
import com.ecommerce.app.model.entity.User;
import com.ecommerce.app.service.UserService;
import com.ecommerce.app.utils.Enum.SuccessCode;
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
    public ResponseEntity<AppResponse<UserResponse>> getUserByUid(@PathVariable Long uid) {
        UserResponse userResponse = userService.getUserResponseByUid(uid);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.FETCHED,
                        userResponse
                )
        );
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<AppResponse<String>> deleteUser(@PathVariable Long uid) {
        userService.delete(uid);
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.DELETED,
                "User deleted"
        ));
    }

    @PutMapping("/{uid}")
    public ResponseEntity<AppResponse<UserResponse>> updateUser(@PathVariable Long uid, @RequestBody UserForm userForm) {
       return ResponseEntity.ok(AppResponse.builderResponse(
               SuccessCode.UPDATED,
               userService.updateInfo(uid, userForm)
       ));
    }

    @PutMapping("/avatar/{uid}")
    public ResponseEntity<AppResponse<UserResponse>> updateAvatar(
            @PathVariable Long uid,
            @RequestParam("avatar") MultipartFile avatar
    ) {
        UserResponse response = userService.updateAvatar(uid, avatar);
        return ResponseEntity.ok(
                AppResponse.builderResponse(
                        SuccessCode.UPDATED,
                        response
                )
        );
    }
}
