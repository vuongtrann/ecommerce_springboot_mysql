package com.ecommerce.app.model.dao.response;

import com.ecommerce.app.utils.SuccessCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppResponse <T>{
    private int statusCode;
    private Object message;
    private T data;

    public static <T> AppResponse<T> builderResponse(SuccessCode successCode, T data) {
        return AppResponse.<T>builder()
                .statusCode(successCode.getCode())
                .message(successCode.getMessage())
                .data(data)
                .build();
    }

    public static <T> AppResponse<T> builderError(int errorCode, String errorMessage) {
        return AppResponse.<T>builder()
                .statusCode(errorCode)
                .message(errorMessage)
                .data(null)
                .build();
    }
}
