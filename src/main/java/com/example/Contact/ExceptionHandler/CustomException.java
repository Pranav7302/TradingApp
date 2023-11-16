package com.example.Contact.ExceptionHandler;

public class CustomException extends RuntimeException{
    String path;

    public CustomException(String message, String path) {
        super(message);
        this.path = path;
    }
}
