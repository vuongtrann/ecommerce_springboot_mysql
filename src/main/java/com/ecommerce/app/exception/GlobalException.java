package com.ecommerce.app.exception;

import com.ecommerce.app.model.dao.response.AppResponse;
import com.ecommerce.app.utils.ErrorCode;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AppResponse> handlingRuntimeException(RuntimeException ex, WebRequest request) {

        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        // Create a custom API response object
        AppResponse response = new AppResponse(errorCode.getCode(), errorCode.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }




//    @ExceptionHandler(AuthorizationDeniedException.class)
//    public ResponseEntity<AppResponse> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
//        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
//        AppResponse AppResponse = AppResponse.builder()
//                .statusCode(errorCode.getCode())
//                .message(errorCode.getMessage())
//                .build();
//        return ResponseEntity.status(errorCode.getCode()).body(AppResponse);
//
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<AppResponse> handleBadCredentialsException(BadCredentialsException e) {
//        ErrorCode errorCode = ErrorCode.BAD_CREDENTIALS;
//        AppResponse AppResponse = AppResponse.builder()
//                .statusCode(errorCode.getCode())
//                .message(errorCode.getMessage())
//                .build();
//        return ResponseEntity.status(errorCode.getCode()).body(AppResponse);
//
//    }


    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<AppResponse> handleMissingRequestCookieException(MissingRequestCookieException ex, WebRequest request) {

        ErrorCode errorCode = ErrorCode.MISSING_REQUEST_COOKIE;
        AppResponse response = new AppResponse(errorCode.getCode(), errorCode.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<AppResponse> handleNumberFormatException(NumberFormatException ex, WebRequest request) {

        ErrorCode errorCode = ErrorCode.WRONG_INPUT;
        AppResponse response = new AppResponse(errorCode.getCode(), errorCode.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<AppResponse> handlingRuntimeException(RuntimeException exception) {
        log.error("Exception: ", exception);
        AppResponse AppResponse = new AppResponse();

        AppResponse.setStatusCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        AppResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(AppResponse);
    }


    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    ResponseEntity<AppResponse> handlingMaxUploadSizeExceededException(MaxUploadSizeExceededException exception) {
        log.error("Exception: ", exception);
        AppResponse AppResponse = new AppResponse();

        AppResponse.setStatusCode(ErrorCode.INVALID_FILE_SIZE.getCode());
        AppResponse.setMessage(ErrorCode.INVALID_FILE_SIZE.getMessage());

        return ResponseEntity.badRequest().body(AppResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<AppResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        AppResponse AppResponse = new AppResponse();

        AppResponse.setStatusCode(errorCode.getCode());
        AppResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(AppResponse);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<AppResponse<Object>> validationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();

        AppResponse<Object> res = new AppResponse<>();

        List<String> errors = fieldErrors.stream().map(f -> f.getDefaultMessage()).collect(Collectors.toList());
        res.setStatusCode(400);
        res.setMessage(errors.size() > 1 ? errors : errors.get(0));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<AppResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        ErrorCode errorCode = ErrorCode.WRONG_INPUT;
        if (ex.getCause() != null && ex.getCause().getCause() instanceof InvalidFormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(AppResponse.<String>builder()
                            .statusCode(errorCode.getCode())
                            .data(errorCode.getMessage())
                            .build());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(AppResponse.<String>builder()
                        .statusCode(errorCode.getCode())
                        .data(errorCode.getMessage())
                        .build());
    }
}
