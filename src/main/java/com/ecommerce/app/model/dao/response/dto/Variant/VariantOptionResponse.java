package com.ecommerce.app.model.dao.response.dto.Variant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariantOptionResponse {
    private Long id;
    private String value;
    private VariantTypeResponse variantType;
}
