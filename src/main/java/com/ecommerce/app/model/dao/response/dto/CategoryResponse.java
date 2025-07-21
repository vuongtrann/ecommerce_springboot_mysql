package com.ecommerce.app.model.dao.response.dto;

import com.ecommerce.app.utils.Enum.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class CategoryResponse {
    private String id;
    private String name;
    private String slug;
    private Status status;
    private Integer totalProduct;

}

