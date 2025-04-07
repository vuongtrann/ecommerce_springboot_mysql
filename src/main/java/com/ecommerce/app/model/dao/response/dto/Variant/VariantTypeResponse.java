package com.ecommerce.app.model.dao.response.dto.Variant;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VariantTypeResponse {
    private Long id;
    private String type;
}