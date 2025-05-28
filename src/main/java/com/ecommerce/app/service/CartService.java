package com.ecommerce.app.service;

import com.ecommerce.app.model.dao.request.CartForm;
import com.ecommerce.app.model.dao.response.dto.CartResponse;
import com.ecommerce.app.model.entity.Cart;

public interface CartService {
    Cart getOrCreateCart(Long userId);
    CartResponse getuserCart(Long userId);
    CartResponse addToCart(Long userId, String productId, int quantity);
    CartResponse removeFromCart(Long userId, String productId);
    CartResponse updateQuantity(Long userId, String productId, int newQuantity);
    void clearCart(Long userId);
    CartResponse syncCartFormClient(CartForm form, boolean merge);
}
