package com.ecommerce.ecommercespringbootmysql.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(500,"Internal Server Error" ,HttpStatus.INTERNAL_SERVER_ERROR ),

    /** Category */
    CATEGORY_NOT_FOUND(404, "Category not found", HttpStatus.NOT_FOUND),
    CATEGORY_ALREADY_EXISTS(400, "Category already exists", HttpStatus.BAD_REQUEST),



    /** File */
    MISSING_REQUIRE_PARAM(400, "Missing Require Param!", HttpStatus.BAD_REQUEST),
    INVALID_FILE_EXTENSION(400, "Invalid File Extension!", HttpStatus.BAD_REQUEST),
    INVALID_FILE_MIME_TYPE(400, "Invalid File Mime Type!", HttpStatus.BAD_REQUEST),
    INVALID_FILE_SIZE(400, "Invalid File Size!", HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND(404, "File Not Found!", HttpStatus.NOT_FOUND),

    WRONG_INPUT(400, "Wrong input in request!", HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN(401, "Invalid or expired token", HttpStatus.BAD_GATEWAY),
    MISSING_REQUEST_COOKIE(400, "Missing Request Cookie!", HttpStatus.BAD_REQUEST),


    PRODUCT_NOT_FOUND(404,"Product not found !" , HttpStatus.NOT_FOUND ),
    PRODUCT_ALREADY_EXISTS(400,"Product already exists !" , HttpStatus.BAD_REQUEST ),
    PRODUCT_NOT_FOUND_BY_SLUG(404,"Product not found by slug !" , HttpStatus.NOT_FOUND ),
    PRODUCT_CANNOT_DELETE(400,"Can not delete this product, please change status first !" , HttpStatus.BAD_REQUEST ),;
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = httpStatusCode;
    }

}
