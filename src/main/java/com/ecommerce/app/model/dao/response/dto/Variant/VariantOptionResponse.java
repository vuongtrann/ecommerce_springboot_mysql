package com.ecommerce.app.model.dao.response.dto.Variant;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VariantOptionResponse {
    private Long id;
    private String value;
    private VariantTypeResponse variantType;
}
