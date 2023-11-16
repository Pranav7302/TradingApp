package com.example.Contact.Service;

import com.example.Contact.Constants.Constants;
import com.example.Contact.Entity.UserInfo;
import com.example.Contact.ExceptionHandler.DuplicateEntryException;
import com.example.Contact.Repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RegisterImpl implements RegisterService {
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public UserInfo registerUser(UserInfo userInfo)
    {
       try{
           userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));  //encrypting the password and saving in the database
           return userInfoRepository.save(userInfo);
       }catch(Exception e)
        {
            Optional<UserInfo> emailExist = userInfoRepository.findByEmail(userInfo.getEmail()); //check email id is already present in user_info table
            Optional<UserInfo> usernameExist = userInfoRepository.findByUserName(userInfo.getUserName());//check username is already present in user_info table
            if(emailExist.isPresent())
            {
                throw new DuplicateEntryException(Constants.Duplicate_Email,Constants.Trading+Constants.Trading_Registration);
            } else if (usernameExist.isPresent()) {
                throw new DuplicateEntryException(Constants.Duplicate_UserName,Constants.Trading+Constants.Trading_Registration);
            }
            else
            {
                throw new RuntimeException(e);
            }
        }
    }
}
