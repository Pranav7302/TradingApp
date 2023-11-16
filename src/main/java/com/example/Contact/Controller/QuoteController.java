package com.example.Contact.Controller;

import com.example.Contact.Constants.Constants;
import com.example.Contact.Constants.HelperMethod;
import com.example.Contact.Dto.Symbol;
import com.example.Contact.Dto.TradeRequest;
import com.example.Contact.Dto.TradeResponse;
import com.example.Contact.Service.QuoteService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequestMapping(Constants.Quote)
@RestController
public class QuoteController {
    @Autowired
    QuoteService quoteService;

    @GetMapping(Constants.Get_Symbol)               //Get the symbol details from symbol table based on SYMBOL
    public TradeResponse getSymbolDetails(@RequestBody TradeRequest tradeRequest) {
        Symbol symbol = quoteService.getSymbol(tradeRequest);
        log.info("Retrieved Symbol from Symbol table, method : getSymbolDetails ");
        return HelperMethod.createResponse("symbolDetails",symbol);
    }

    @GetMapping(Constants.Get_All_Symbols)        //Get all the symbols detail
    public TradeResponse getAllSymbolDetails() {
        List<Symbol> symbolList = quoteService.getAllSymbols();
        log.info("Retrieved Symbols from Symbol table, method : getAllSymbolDetails ");
        return HelperMethod.createResponse("allSymbolDetails",symbolList);
    }
}
