package com.example.Contact.Controller;

import com.example.Contact.Constants.Constants;
import com.example.Contact.Constants.HelperMethod;
import com.example.Contact.Dto.TradeRequest;
import com.example.Contact.Dto.TradeResponse;
import com.example.Contact.Entity.UserInfo;
import com.example.Contact.Service.RegisterService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequestMapping(Constants.Trading)
@RestController
public class UserRegisterController {

    @Autowired
    RegisterService registerService;

    @PostMapping(Constants.Trading_Registration)           //create a user
    public TradeResponse addUser(@Valid @RequestBody TradeRequest tradeRequest) {
        UserInfo userInfo = tradeRequest.getUserInfo();
        UserInfo addedUserInfo = registerService.registerUser(userInfo);
        log.info("Registered user, method : addUser ");
        return HelperMethod.createResponse("registeredUser: ", addedUserInfo);
    }

}

