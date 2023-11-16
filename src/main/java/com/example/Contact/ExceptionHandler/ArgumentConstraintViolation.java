package com.example.Contact.ExceptionHandler;

public class ArgumentConstraintViolation extends RuntimeException{
    public ArgumentConstraintViolation(String message) {
        super(message);
    }
}
