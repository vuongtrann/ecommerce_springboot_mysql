package com.ecommerce.ecommercespringbootmysql.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(500,"Internal Server Error" ,HttpStatus.INTERNAL_SERVER_ERROR ),

    /** Category */
    CATEGORY_NOT_FOUND(404, "Category not found", HttpStatus.NOT_FOUND),






;

    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
