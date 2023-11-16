package com.example.Contact.ExceptionHandler;
public class DuplicateEntryException extends RuntimeException {
     String path;
    public DuplicateEntryException(String message, String path) {
        super(message);
        this.path=path;
    }
}
