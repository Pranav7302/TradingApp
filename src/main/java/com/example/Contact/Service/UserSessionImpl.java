package com.example.Contact.Service;

import com.example.Contact.Constants.Constants;
import com.example.Contact.Dto.AuthRequest;
import com.example.Contact.Dto.TradeRequest;
import com.example.Contact.Dto.UserSessionDTO;
import com.example.Contact.Entity.UserSession;
import com.example.Contact.ExceptionHandler.DuplicateEntryException;
import com.example.Contact.ExceptionHandler.UserNotFoundException;
import com.example.Contact.Repository.UserInfoRepository;
import com.example.Contact.Repository.UserSessionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@Component
public class UserSessionImpl implements UserSessionService {
    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    UserSessionRepository userSessionRepository;

    // Method to return existing user session details
    public UserSessionDTO authenticateUser(AuthRequest authRequest)
    {
        UserSessionDTO userSessionDTO = new UserSessionDTO();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            // check whether the user credentials are present in database and authenticate
            if (authentication.isAuthenticated()) {
                String username = authRequest.getUsername();
                userSessionDTO.setUsername(username);
                Long id = userInfoRepository.findByUserNameOrEmail(username,username).get().getId();
                userSessionDTO.setId(id);
                userSessionDTO.setExistingUserSession(userSessionRepository.findByUserId(id));
                log.info(userSessionDTO);
                return userSessionDTO;
            }
        } catch (AuthenticationException e) {
            throw new UserNotFoundException(Constants.User_Not_Found,Constants.User_Session);
        }
        return null;
    }

    public String loginUser(TradeRequest tradeRequest) {
        AuthRequest authRequest = tradeRequest.getAuthRequest();
        UserSessionDTO existingUserSessionDTO = authenticateUser(authRequest);
        String username = existingUserSessionDTO.getUsername();
        Long id = existingUserSessionDTO.getId();
        if(id!=null)
        {
            if (existingUserSessionDTO.getExistingUserSession() == null) {
                String token = jwtService.generateToken(username);
                UserSession userSession = new UserSession();
                userSession.setUserId(id);
                userSession.setToken(token);
                userSessionRepository.save(userSession); // creating user session in database
                return token;
            }
            else
            {
                throw new DuplicateEntryException(Constants.Logged_In,Constants.Session+Constants.Login);
            }
        }
        else
        {
            throw new UserNotFoundException(Constants.User_Not_Found,Constants.Session+Constants.Login);
        }

    }
    public String logoutUser(TradeRequest tradeRequest)
    {
        AuthRequest authRequest = tradeRequest.getAuthRequest();
        log.info("service logout");
        UserSessionDTO existingUserSessionDTO = authenticateUser(authRequest);
        Long id = existingUserSessionDTO.getId();
        if(id!=null)
        {
            if (existingUserSessionDTO.getExistingUserSession() != null) {
                userSessionRepository.deleteByUserId(id);  //deleting the user session from database
                return Constants.Logged_Out;
            }
            else
            {
                throw new DuplicateEntryException(Constants.User_Not_Log_In,Constants.Session+Constants.Logout);

            }
        }
        else {
            throw new UserNotFoundException(Constants.User_Not_Found,Constants.Session+Constants.Logout);
        }
    }

}
