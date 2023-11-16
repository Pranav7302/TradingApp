package com.example.Contact.Dto;

import lombok.Data;

import java.util.Map;


@Data
public class TradeResponse {
    private Map<String, Object> outputResponse; // Use a Map to hold the response data
}
