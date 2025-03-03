package com.ecommerce.ecommercespringbootmysql.exception;

import com.ecommerce.ecommercespringbootmysql.utils.ErrorCode;
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
    public ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException ex, WebRequest request) {

        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        // Create a custom API response object
        ApiResponse response = new ApiResponse(errorCode.getCode(), errorCode.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


//    @ExceptionHandler(AuthorizationDeniedException.class)
//    public ResponseEntity<ApiResponse> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
//        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
//        ApiResponse apiResponse = ApiResponse.builder()
//                .statusCode(errorCode.getCode())
//                .message(errorCode.getMessage())
//                .build();
//        return ResponseEntity.status(errorCode.getCode()).body(apiResponse);
//
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ApiResponse> handleBadCredentialsException(BadCredentialsException e) {
//        ErrorCode errorCode = ErrorCode.BAD_CREDENTIALS;
//        ApiResponse apiResponse = ApiResponse.builder()
//                .statusCode(errorCode.getCode())
//                .message(errorCode.getMessage())
//                .build();
//        return ResponseEntity.status(errorCode.getCode()).body(apiResponse);
//
//    }


    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<ApiResponse> handleMissingRequestCookieException(MissingRequestCookieException ex, WebRequest request) {

        ErrorCode errorCode = ErrorCode.MISSING_REQUEST_COOKIE;
        ApiResponse response = new ApiResponse(errorCode.getCode(), errorCode.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ApiResponse> handleNumberFormatException(NumberFormatException ex, WebRequest request) {

        ErrorCode errorCode = ErrorCode.WRONG_INPUT;
        ApiResponse response = new ApiResponse(errorCode.getCode(), errorCode.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        log.error("Exception: ", exception);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setStatusCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }


    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    ResponseEntity<ApiResponse> handlingMaxUploadSizeExceededException(MaxUploadSizeExceededException exception) {
        log.error("Exception: ", exception);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setStatusCode(ErrorCode.INVALID_FILE_SIZE.getCode());
        apiResponse.setMessage(ErrorCode.INVALID_FILE_SIZE.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setStatusCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ApiResponse<Object>> validationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();

        ApiResponse<Object> res = new ApiResponse<>();

        List<String> errors = fieldErrors.stream().map(f -> f.getDefaultMessage()).collect(Collectors.toList());
        res.setStatusCode(400);
        res.setMessage(errors.size() > 1 ? errors : errors.get(0));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        ErrorCode errorCode = ErrorCode.WRONG_INPUT;
        if (ex.getCause() != null && ex.getCause().getCause() instanceof InvalidFormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<String>builder()
                            .statusCode(errorCode.getCode())
                            .data(errorCode.getMessage())
                            .build());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.<String>builder()
                        .statusCode(errorCode.getCode())
                        .data(errorCode.getMessage())
                        .build());
    }
}
