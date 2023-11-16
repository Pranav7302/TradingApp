package com.example.Contact.Repository;


import com.example.Contact.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUserName(String userName); //retrieve userinfo details

    Optional<UserInfo> findByEmail(String email);

    Optional<UserInfo> findByUserNameOrEmail(String username, String userName);
}