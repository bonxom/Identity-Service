package com.example.identity.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    INVALID_KEY(0001, "Invalid message key"),
    USER_EXISTED(1001, "User existed"),
    USER_NOT_EXISTED(1002, "User not existed"),
    USERNAME_INVALID(2001, "Username must be at least 3 characters"),
    PASSWORD_INVALID(2002, "Password must be at least 8 characters"),
    UNAUTHENTICATED(3001, "Can not authenticate user")
    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
