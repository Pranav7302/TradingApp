package com.example.Contact.Repository;

import com.example.Contact.Entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistGroupRepository extends JpaRepository<Watchlist,Long> {

}
