package com.example.Contact.Dto;

import lombok.Data;

@Data
public class PortfolioItemDTO {
    private String stockSymbol;
    private int quantity;
    private String orderStatus;
    private String timestamp;
}

