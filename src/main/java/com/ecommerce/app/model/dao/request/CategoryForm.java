package com.ecommerce.app.model.dao.request;

import com.ecommerce.app.utils.Enum.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CategoryForm {

    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters")
    private String name;

    private Status status;



}
