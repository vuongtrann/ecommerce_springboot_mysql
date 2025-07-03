package com.ecommerce.app.service;

import com.ecommerce.app.model.dao.request.CartForm;
import com.ecommerce.app.model.dao.response.dto.CartResponse;
import com.ecommerce.app.model.entity.Cart;

public interface CartService {
    Cart getOrCreateCart(Long userUid);
    CartResponse getuserCart(Long userUid);
    CartResponse addToCart(Long userUid, String productId, int quantity);
    CartResponse removeFromCart(Long userUid, String productId);
    CartResponse updateQuantity(Long userUid, String productId, int newQuantity);
    void clearCart(Long userUid);
    CartResponse syncCartFormClient(CartForm form, boolean merge);
}
