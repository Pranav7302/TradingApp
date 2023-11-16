package com.example.Contact.Service;

import com.example.Contact.Dto.TradeRequest;


public interface UserSessionService {
   String loginUser(TradeRequest tradeRequest);

   String logoutUser(TradeRequest tradeRequest);
}
