package com.example.Contact.Dto;

import com.example.Contact.Entity.UserSession;
import lombok.Data;

@Data
public class UserSessionDTO {
    String username;
    Long id;
    UserSession existingUserSession;
}
