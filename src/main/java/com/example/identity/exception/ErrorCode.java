package com.example.identity.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(0001, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1002, "User not existed", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(2001, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(2002, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    DOB_INVALID(2003, "User must be at least 18 years old", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(3001, "Can not authenticate user", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(3002, "User is not allowed to access", HttpStatus.FORBIDDEN)
    ;
    private int code;
    private String message;
    private HttpStatus statusCode;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
