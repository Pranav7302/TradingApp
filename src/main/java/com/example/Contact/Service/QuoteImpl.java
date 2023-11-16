package com.example.Contact.Service;

import com.example.Contact.Constants.Constants;
import com.example.Contact.Dto.Symbol;
import com.example.Contact.Dto.TradeRequest;
import com.example.Contact.Repository.SymbolRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Log4j2
public class QuoteImpl implements QuoteService{
    @Autowired
    SymbolRepository symbolRepository;
    public Symbol getSymbol(TradeRequest tradeRequest)      //get symbol details from symbol table using SYMBOL value as input
    {
        String symbolName = tradeRequest.getSymbolName();
        log.info("Returned by quote service method : getSymbol");
        return symbolRepository.findSymbolBySymbolName(symbolName);
    }
    @Cacheable(value = Constants.Symbol_Value, key = Constants.Symbol_Key,cacheManager = Constants.Cache_Manger, unless = Constants.Unless )
    public List<Symbol> getAllSymbols() {   //get all the symbol details from symbol table
        log.info("Returned by Quote service method : getAllSymbols");
        return symbolRepository.findAllSymbols();
    }

}
