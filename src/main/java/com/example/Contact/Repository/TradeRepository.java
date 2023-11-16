package com.example.Contact.Repository;

import com.example.Contact.Entity.Trade;
import com.example.Contact.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade,Long> {

    List<Trade> findByUserTradeIdAndOrderType(UserInfo currentUserInfo, String buy);

    List<Trade> findByUserTradeId(UserInfo currentUserInfo);
}
