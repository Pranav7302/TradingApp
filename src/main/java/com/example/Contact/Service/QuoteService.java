package com.example.Contact.Service;

import com.example.Contact.Dto.Symbol;
import com.example.Contact.Dto.TradeRequest;

import java.util.List;

public interface QuoteService {
    Symbol getSymbol(TradeRequest tradeRequest);
    List<Symbol> getAllSymbols();

}
