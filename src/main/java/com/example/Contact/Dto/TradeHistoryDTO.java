package com.example.Contact.Dto;

import lombok.Data;

@Data
public class TradeHistoryDTO {
    private String stockSymbol;
    private int quantity;
    private double price;
    private String orderType;
    private String orderStatus;
    private String timestamp;
}