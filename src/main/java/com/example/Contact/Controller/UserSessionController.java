package com.example.Contact.Controller;

import com.example.Contact.Constants.Constants;
import com.example.Contact.Constants.HelperMethod;
import com.example.Contact.Dto.TradeRequest;
import com.example.Contact.Dto.TradeResponse;
import com.example.Contact.Service.UserSessionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping(Constants.Session)
public class UserSessionController {

    @Autowired
    UserSessionService userSessionService;

    @PostMapping(Constants.Login) // create user session and get token
    public TradeResponse authenticateAndGetToken(@RequestBody TradeRequest tradeRequest) {
        log.info("User login requested, method: authenticateAndGetToken");
        String token = userSessionService.loginUser(tradeRequest);
        log.info("User logged in successfully");
        return HelperMethod.createResponse("Token", token);
    }

    @PostMapping(Constants.Logout) // user session is invalidated
    public TradeResponse userLogout(@RequestBody TradeRequest tradeRequest) {
        log.info("User logout requested, method: userLogout");
        String message = userSessionService.logoutUser(tradeRequest);
        log.info("User logged out successfully");
        return HelperMethod.createResponse("Message", message);
    }
}
