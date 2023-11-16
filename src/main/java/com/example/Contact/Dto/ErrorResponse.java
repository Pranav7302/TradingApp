package com.example.Contact.Dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private String errorName;
    private String path;
    private String message;
    private String errorCode;
}

