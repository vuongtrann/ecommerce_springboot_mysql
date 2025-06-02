package com.ecommerce.app.model.mapper;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.request.ItemForm;
import com.ecommerce.app.model.dao.response.dto.CartResponse;
import com.ecommerce.app.model.dao.response.dto.ItemResponse;
import com.ecommerce.app.model.entity.Cart;
import com.ecommerce.app.model.entity.Item;
import com.ecommerce.app.model.entity.Product;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.utils.Enum.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartMapper {
    private final ProductRepository productRepository;

    public Item toItem(ItemForm form){
        Product product = productRepository.findById(form.getProductId()).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Item item = new Item();
        item.setProduct(product);
        item.setQuantity(form.getQuantity());
        double price = product.getSellingPrice();
        item.setUnitPrice(price);
        return item;
    }

    public CartResponse toCartResponse(Cart cart){
        List<ItemResponse> itemResponses = cart.getItems().stream().map(item -> {
            Product product = item.getProduct();
            return new ItemResponse(
                    product.getId(),
                    product.getName(),
                    product.getPrimaryImageURL(),
                    item.getQuantity(),
                    item.getUnitPrice()
            );
        }).collect(Collectors.toList());

        return new CartResponse(
                cart.getId(),
                cart.getUserId(),
                itemResponses
        );

    }
}
