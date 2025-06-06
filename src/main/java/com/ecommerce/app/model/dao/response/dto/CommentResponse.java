package com.ecommerce.app.model.dao.response.dto;

import com.ecommerce.app.model.entity.Product;
import com.ecommerce.app.model.entity.User;
import com.ecommerce.app.utils.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse {
    private String commentId;
    private Long userUid;
    private String content;
    private Status status = Status.ACTIVE;
    private String productId;
    private String firstName;
    private String lastName;
    private String avatar;
    private String email;
}
