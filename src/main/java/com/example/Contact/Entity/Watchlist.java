package com.example.Contact.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;

    private String groupName;

    @ElementCollection
    @CollectionTable(
            name = "watchlist_group_symbols",
            joinColumns = @JoinColumn(name = "watchlist_group_id")
    )
    @Column(name = "symbol_name")
    private List<String> symbols = new ArrayList<>();
}
