package com.example.Contact.Dto;

import com.example.Contact.Entity.Order;
import com.example.Contact.Entity.UserInfo;
import lombok.Data;


@Data
public class TradeRequest {
    UserInfo userInfo;
    AuthRequest authRequest;
    String searchString;
    Long userId;
    String userName;
    Long groupId;
    String groupName;
    AddSymbol symbol;
    String symbolName;
    Order order;
    String orderId;
}
