package com.example.Contact.Constants;

import com.example.Contact.Dto.TradeResponse;

import java.util.HashMap;
import java.util.Map;

public class HelperMethod {

    // Helper method to create a response map
    public static TradeResponse createResponse(String key, String value) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put(key, value);
        TradeResponse tradeResponse = new TradeResponse();
        tradeResponse.setOutputResponse(responseMap);
        return tradeResponse;
    }
    public static TradeResponse createResponse(String key, Object value) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put(key, value);
        TradeResponse tradeResponse = new TradeResponse();
        tradeResponse.setOutputResponse(responseMap);
        return tradeResponse;
    }
}
