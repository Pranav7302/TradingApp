package com.example.Contact.ExceptionHandler;

public class UserNotFoundException extends RuntimeException {
    private final String requestPath;
    public UserNotFoundException(String message,String requestPath) {
        super(message);
        this.requestPath = String.valueOf(requestPath);
    }

    public String getRequestPath() {
        return requestPath;
    }
}

