package com.example.Contact.Config;

import com.example.Contact.Constants.Constants;
import com.example.Contact.Entity.UserInfo;
import com.example.Contact.Repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    UserInfoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfoByUserName = repository.findByUserName(username);
        if (userInfoByUserName.isPresent()) {
            return new UserInfoUserDetails(userInfoByUserName.get());//call userInfoUserDetails method with username
        }
        Optional<UserInfo> userInfoByEmail = repository.findByEmail(username);
        return userInfoByEmail.map(userInfo -> new UserInfoUserDetails(userInfo, true))//call userInfoUserDetails with email as username
                .orElseThrow(() -> new UsernameNotFoundException(Constants.User_Not_Found + username));
    }
}
