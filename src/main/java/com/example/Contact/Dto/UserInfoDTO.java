package com.example.Contact.Dto;



import lombok.Data;


import java.util.List;

@Data
public class UserInfoDTO {
    private Long id;
    private String username;
    private List<WatchlistGroupDTO> watchlistGroups;
}



