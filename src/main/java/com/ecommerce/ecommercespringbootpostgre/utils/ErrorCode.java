package com.ecommerce.ecommercespringbootpostgre.utils;

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
    PRODUCT_CANNOT_DELETE(400,"Can not delete this product, please change status first !" , HttpStatus.BAD_REQUEST ),

    TAG_NOT_FOUND(404, "Tag not found !", HttpStatus.NOT_FOUND),
    TAG_ALREADY_EXISTS(400,"Tag already exists !" , HttpStatus.BAD_REQUEST ),
    TAG_CANNOT_DELETE(400,"Can not delete tag !" , HttpStatus.BAD_REQUEST ),
    TAG_STATUS_IS_ACTIVE(400,"Tag status is active , please change status first !" , HttpStatus.BAD_REQUEST ),

    EMAIL_ALREADY_EXISTS(400,"Email already exists !" , HttpStatus.BAD_REQUEST ),
    INVALID_CREDENTIALS(400,"Invalid credentials !" , HttpStatus.BAD_REQUEST ),
    ACCOUNT_NOT_VERIFIED(400,"Account not verified !" , HttpStatus.FORBIDDEN ),
    INVALID_VERIFICATION_TOKEN(400,"Invalid verification token" , HttpStatus.BAD_REQUEST ),
    USER_NOT_FOUND(404,"User not found !" , HttpStatus.NOT_FOUND ),
    COLLECTION_NOT_FOUND(404,"Collection not found !" , HttpStatus.NOT_FOUND ),
    COLLECTION_CANNOT_DELETE(400,"Collection Cannot delete !" , HttpStatus.BAD_REQUEST ),

    BRAND_NOT_FOUND(404,"Brand not found !" , HttpStatus.NOT_FOUND ),
    BRAND_CANNOT_DELETE(400,"Brand not found !" , HttpStatus.BAD_REQUEST ),

    BANNER_NOT_FOUND(404,"Banner not found !" , HttpStatus.NOT_FOUND ),
    BANNER_CANNOT_DELETE(400,"Banner not found !" , HttpStatus.BAD_REQUEST ),
    VARIANT_TYPE_NOT_FOUND(404,"Variant type not found !" , HttpStatus.NOT_FOUND );

  private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = httpStatusCode;
    }

}
