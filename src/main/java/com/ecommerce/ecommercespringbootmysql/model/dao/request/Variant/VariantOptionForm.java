package com.ecommerce.ecommercespringbootmysql.model.dao.request.Variant;

import lombok.Data;

@Data
public class VariantOptionForm {
    private String value;  // Giá trị của lựa chọn biến thể (ví dụ: "Red", "Blue", "Small", "Large")
    private Long variantTypeId;  // ID loại biến thể (ví dụ: Color, Size)
}
