package com.ecommerce.app.service.impl;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.request.CartForm;
import com.ecommerce.app.model.dao.request.ItemForm;
import com.ecommerce.app.model.dao.response.dto.CartResponse;
import com.ecommerce.app.model.entity.Cart;
import com.ecommerce.app.model.entity.Item;
import com.ecommerce.app.model.entity.Product;
import com.ecommerce.app.model.mapper.CartMapper;
import com.ecommerce.app.repository.CartRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.service.CartService;
import com.ecommerce.app.utils.Enum.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements CartService {
    CartRepository cartRepository;
    ProductRepository productRepository;
    CartMapper cartMapper;

    @Override
    public Cart getOrCreateCart(Long userUid) {
        return cartRepository.findByUserUid(userUid)
                .orElseGet(()->{
                    Cart newCart = new Cart();
                    newCart.setUserUid(userUid);
                    return cartRepository.save(newCart);
                });
    }

    @Override
    public CartResponse getuserCart(Long userUid) {
        Cart cart = getOrCreateCart(userUid);
        CartResponse cartResponse = cartMapper.toCartResponse(cart);
        return cartResponse;
    }

    @Override
    public CartResponse addToCart(Long userUid, String productId, int quantity) {
        Cart cart = getOrCreateCart(userUid);
        Product product = productRepository.findById(productId).orElseThrow(()->
                new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Optional<Item> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        if (existingItem.isPresent()) {
            Item item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);


        } else {
            Item newItem = new Item();
            newItem.setProduct(product);
            if(quantity == 0){
                quantity = 1;
            }
            newItem.setQuantity(quantity);
//            product.setQuantity(product.getQuantity() - quantity);
            double price = product.getSellingPrice();
            newItem.setUnitPrice(price);
            cart.getItems().add(newItem);
        }

        Cart savedCart = cartRepository.save(cart);
        CartResponse cartResponse = cartMapper.toCartResponse(savedCart);
        return cartResponse;
    }

    @Override
    public CartResponse removeFromCart(Long userUid, String productId) {
        Cart cart = getOrCreateCart(userUid);

        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        Cart savedCart = cartRepository.save(cart);
        CartResponse cartResponse = cartMapper.toCartResponse(savedCart);
        return cartResponse;
    }

    @Override
    public CartResponse updateQuantity(Long userUid, String productId, int newQuantity) {
        Cart cart = getOrCreateCart(userUid);


        for (Item item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                if (newQuantity <= 0) {
                    newQuantity = 1;
                    item.setQuantity(newQuantity);
                    break;
//                    cart.getItems().remove(item);
//                    break;
                }
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

                if (newQuantity > product.getQuantity()) {
                    throw new AppException(ErrorCode.NOT_ENOUGH_PRPDUCTS);
                }
                else {
                    item.setQuantity(newQuantity);
//                    product.setQuantity(product.getQuantity() - newQuantity);
                }
                break;
            }
        }

        Cart savedCart = cartRepository.save(cart);
        CartResponse cartResponse = cartMapper.toCartResponse(savedCart);
        return cartResponse;
    }

    @Override
    public void clearCart(Long userUid) {
        Cart cart = getOrCreateCart(userUid);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartResponse syncCartFormClient(CartForm form, boolean merge) {
        Cart cart = getOrCreateCart(form.getUserUid());
        if (!merge) {
            cart.getItems().clear();
        }
        for (ItemForm itemForm : form.getItems()) {
            String productId = itemForm.getProductId();
            Optional<Item> existingItem = cart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();
            if (existingItem.isPresent()) {
                Item item = existingItem.get();
                item.setQuantity(item.getQuantity() +itemForm.getQuantity());
            } else {
                cart.getItems().add(cartMapper.toItem(itemForm));
            }
        }
        Cart savedCart =  cartRepository.save(cart);
        CartResponse cartResponse = cartMapper.toCartResponse(savedCart);
        return cartResponse;
    }
}
