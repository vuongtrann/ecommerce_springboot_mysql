package com.ecommerce.ecommercespringbootpostgre.model.dao.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CategoryForm {

    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters")
    private String name;

    private String slug;

    private String icon;

    private String banner;

    private String parentId;

    private List<String> childId;

}
