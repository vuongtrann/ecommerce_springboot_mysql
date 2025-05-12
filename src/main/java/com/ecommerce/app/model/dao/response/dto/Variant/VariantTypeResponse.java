package com.ecommerce.app.model.dao.response.dto.Variant;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantTypeResponse {
    private Long id;
    private String type;
}