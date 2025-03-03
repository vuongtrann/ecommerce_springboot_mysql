package com.ecommerce.ecommercespringbootmysql.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum SuccessCode {

    CREATED(201, "Created Successfully!", HttpStatus.CREATED),
    DELETED (200, "Deleted Successfully!", HttpStatus.OK),
    UPDATED(200, "Updated Successfully", HttpStatus.OK),
    FETCHED(200, "Get Successfully" , HttpStatus.OK), UPLOADED( 200, "Uploaded Successfully", HttpStatus.OK),
    REGISTER(200, "User Registered Successfully", HttpStatus.OK),
    LOGIN(200, "Success", HttpStatus.OK),
    LOGOUT(200, "You've been signed out!", HttpStatus.OK),
    TOKEN_REFRESH(200, "Token refreshed successfully", HttpStatus.OK),
    SEND_MAIL_FORGOT_PASSWORD( 200, "Password reset link sent to your email" , HttpStatus.OK ),
    VERIFY_FORGOT_PASSWORD( 200, "Password changed successfully", HttpStatus.OK ),
    VERIFY_EMAIL( 200, "Email verified successfully", HttpStatus.OK ),
    VERIFY_ACCOUNT( 200, "Account verified successfully", HttpStatus.OK ),
    SEND_MAIL_VERIFY_ACCOUNT( 200, "Confirmation link sent to your email", HttpStatus.OK ),
    CHANGE_PASSWORD( 200, "Password changed successfully",  HttpStatus.OK ),
    CHANGE_USERNAME( 200, "Username changed successfully",  HttpStatus.OK ),


    CAN_NOT_ADD_PAYMENT_METHOD(400, "Can not add payment method", HttpStatus.BAD_REQUEST),
    CAN_NOT_CREATE_CUSTOMER(400, "Can not create customer", HttpStatus.BAD_REQUEST),
    FORGOT_PASSWORD(200, "New random password have sent to your email", HttpStatus.OK),;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    SuccessCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
