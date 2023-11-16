package com.example.Contact.Mockito;

import com.example.Contact.Constants.Constants;
import com.example.Contact.Controller.UserRegisterController;
import com.example.Contact.Dto.TradeRequest;
import com.example.Contact.Dto.TradeResponse;
import com.example.Contact.Entity.Order;
import com.example.Contact.Entity.Trade;
import com.example.Contact.Entity.UserInfo;
import com.example.Contact.Repository.OrderRepository;
import com.example.Contact.Repository.UserInfoRepository;
import com.example.Contact.Service.DashboardImpl;
import com.example.Contact.Service.RegisterService;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@Log4j2
@RunWith(MockitoJUnitRunner.class)
public class UserRegisterControllerTest {

    @InjectMocks
    private UserRegisterController userRegisterController;

    @Mock
    private RegisterService registerService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserInfoRepository userRepository;

    @InjectMocks
    DashboardImpl dashboard;

    @Test
    public void testAddUser() {
        // Create a sample UserInfo object
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setUserName("testUser");

        // Create a sample TradeRequest with the UserInfo
        TradeRequest tradeRequest = new TradeRequest();
        tradeRequest.setUserInfo(userInfo);

        // Mock the behavior of registerService.registerUser
        when(registerService.registerUser(userInfo)).thenReturn(userInfo);

        // Call the addUser method
        TradeResponse response = userRegisterController.addUser(tradeRequest);

        // Verify that registerService.registerUser was called
        verify(registerService, times(1)).registerUser(userInfo);

        // Check the response
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("registeredUser: ", userInfo);

        assertEquals(expectedResponse, response.getOutputResponse());
        log.info("addUser, Test Successful");
    }

    @Test
    public void testExecuteOrders() {
        // Mock buy and sell orders
        List<Order> buyOrders = new ArrayList<>();
        List<Order> sellOrders = new ArrayList<>();


        // Add mock buy and sell orders to the lists

        when(orderRepository.findBuyOrders()).thenReturn(buyOrders);
        when(orderRepository.findSellOrders()).thenReturn(sellOrders);

        // Mock user objects
        UserInfo buyOrderUser = new UserInfo();
        UserInfo sellOrderUser = new UserInfo();

        // Create mock buy and sell orders and associate them with users
        Order buyOrder = createMockOrder(buyOrderUser, "ABC", 10.0, Constants.Pending, 10);
        Order sellOrder = createMockOrder(sellOrderUser, "ABC", 9.0, Constants.Pending, 15);

        // Add the buy and sell orders to the respective lists
        buyOrders.add(buyOrder);
        sellOrders.add(sellOrder);



        // Call the executeOrders method
        dashboard.executeOrders();

        // Verify that expected methods were called
        verify(orderRepository).findBuyOrders();
        verify(orderRepository).findSellOrders();

        // Assert that your business logic works correctly
        // You can check the updated status of orders, trade history, etc.
        assertEquals(9.0, buyOrder.getPrice(), 0.001);
        assertEquals(0, buyOrder.getQuantity());
        assertEquals(5, sellOrder.getQuantity());
    }

    // Helper method to create a mock order
    private Order createMockOrder(UserInfo user, String stockSymbol, double price, String status, int quantity) {
        Order order = new Order();
        order.setUserOrdersId(user); // Associate with the user
        order.setStockSymbol(stockSymbol);
        order.setPrice(price);
        order.setStatus(status);
        order.setQuantity(quantity);
        return order;
    }


}
