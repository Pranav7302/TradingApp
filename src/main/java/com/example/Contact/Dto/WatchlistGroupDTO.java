package com.example.Contact.Dto;

import lombok.Data;

import java.util.List;
@Data
public class WatchlistGroupDTO {
    private Long id;
    private String groupName;
    private List<String> symbols;
}