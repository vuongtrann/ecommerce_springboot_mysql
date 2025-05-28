package com.ecommerce.app.controller;

import com.ecommerce.app.model.dao.request.CartForm;
import com.ecommerce.app.model.dao.response.AppResponse;
import com.ecommerce.app.model.dao.response.dto.CartResponse;
import com.ecommerce.app.model.entity.Cart;
import com.ecommerce.app.model.mapper.CartMapper;
import com.ecommerce.app.service.CartService;
import com.ecommerce.app.utils.Enum.SuccessCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    CartService cartService;
    CartMapper cartMapper;

    @PostMapping("/add")
    public ResponseEntity<AppResponse<CartResponse>> addToCart(@RequestParam Long userId, @RequestParam String productId, @RequestParam int quantity) {
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.CREATED,
                cartService.addToCart(userId, productId, quantity)
        ));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<AppResponse<CartResponse>> removeFromCart(@RequestParam Long userId, @RequestParam String productId) {
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.DELETED,
                cartService.removeFromCart(userId, productId)
        ));
    }

    @PutMapping("/update")
    public ResponseEntity<AppResponse<CartResponse>> updateQuantity(@RequestParam Long userId, @RequestParam String productId, @RequestParam int newQuantity) {
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.UPDATED,
                cartService.updateQuantity(userId, productId, newQuantity)
        ));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<AppResponse<String>> clearCart(@RequestParam Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.CLEARED,
                "Cart cleared successfully !"
        ));
    }

    @GetMapping
    public ResponseEntity<AppResponse<CartResponse>> getUserCart(@RequestParam Long userId) {
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.FETCHED,
                cartService.getuserCart(userId)
        ));
    }

    @PostMapping("/sync")
    public ResponseEntity<AppResponse<CartResponse>> syncCart(@RequestBody CartForm form, @RequestParam boolean merge) {
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.SYNCED,
                cartService.syncCartFormClient(form, merge)
        ));
    }
}
