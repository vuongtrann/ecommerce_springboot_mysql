package com.ecommerce.ecommercespringbootmysql.model.dao.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TagForm {
    @NotBlank(message = "Tag Name is required")
    @Size(min = 5, max = 50, message = "Tag Name must be between 5 and 50 characters")
    private String tagName;
}
