package com.ecommerce.app.model.dao.response.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavouriteResponse {
    private String favouriteId;
    private Long userUid;
    private List<ProductResponse> products;

}
