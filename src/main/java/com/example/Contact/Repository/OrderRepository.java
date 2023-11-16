package com.example.Contact.Repository;

import com.example.Contact.Entity.Order;
import com.example.Contact.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT o FROM Order o WHERE o.orderType = 'BUY'")
    List<Order> findBuyOrders();

    @Query("SELECT o FROM Order o WHERE o.orderType = 'SELL'")
    List<Order> findSellOrders();

    List<Order> findByUserOrdersIdAndOrderType(UserInfo userInfo, String orderType);

    List<Order> findByUserOrdersId(UserInfo currentUserInfo);
}
