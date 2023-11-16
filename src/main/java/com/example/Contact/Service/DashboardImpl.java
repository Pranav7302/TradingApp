package com.example.Contact.Service;

import com.example.Contact.Constants.Constants;
import com.example.Contact.Dto.*;
import com.example.Contact.Entity.Order;
import com.example.Contact.Entity.Trade;
import com.example.Contact.Entity.UserInfo;
import com.example.Contact.Entity.Watchlist;
import com.example.Contact.ExceptionHandler.ArgumentConstraintViolation;
import com.example.Contact.ExceptionHandler.CustomException;
import com.example.Contact.ExceptionHandler.DuplicateEntryException;
import com.example.Contact.ExceptionHandler.UserNotFoundException;
import com.example.Contact.Filter.JwtAuthFilter;
import com.example.Contact.Repository.OrderRepository;
import com.example.Contact.Repository.SymbolRepository;
import com.example.Contact.Repository.TradeRepository;
import com.example.Contact.Repository.UserInfoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@EnableScheduling
public class DashboardImpl implements DashboardService {
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    SymbolRepository symbolRepository;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TradeRepository tradeRepository;


    //method to check whether a watchlist group is present or not and return it
    private Watchlist checkWatchlistGroupExists(UserInfo userInfo, Long groupId,String path) {
        return userInfo.getWatchlists().stream()
                .filter(group -> Objects.equals(group.getId(), groupId))
                .findFirst()
                .orElseThrow(() -> new DuplicateEntryException(Constants.Grp_Id_Not_Found_User, path));

    }


    // Method to retrieve the current user's UserInfo
    public UserInfo getCurrentUserInfo() {
        String userName = JwtAuthFilter.userNameMatches;
        Optional<UserInfo> existingUser = userInfoRepository.findByUserNameOrEmail(userName,userName);
        return existingUser.orElseThrow(() -> new UserNotFoundException(Constants.User_Not_Found, Constants.User_Session));
    }


    //Get Current TimeStamp
    public String getCurrentTime() {
        LocalDateTime currentTimestamp = LocalDateTime.now();

        // Define the desired format pattern
        String pattern = Constants.Time_Stamp_Pattern;

        // Create a DateTimeFormatter with the pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        // Format the current timestamp using the formatter

        return currentTimestamp.format(formatter);
    }


    // Method to add a new watchlist group to the current user
    public String addWatchlistGroup(TradeRequest tradeRequest) {
        String groupName = tradeRequest.getGroupName();
        log.info("service group name: " + groupName);
        UserInfo userInfo = getCurrentUserInfo();
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(userInfo);
        watchlist.setGroupName(groupName);
        List<String> symbols = new ArrayList<>();

        // Set symbols for the watchlist group
        watchlist.setSymbols(symbols);
        userInfo.getWatchlists().add(watchlist);
        UserInfo updatedUserInfo = userInfoRepository.save(userInfo);

        List<Watchlist> watchlists = updatedUserInfo.getWatchlists();
        Watchlist lastWatchlist = watchlists.get(watchlists.size() - 1);
        Long id = lastWatchlist.getId();
        return Constants.Watchlist_Group+id;
    }


    // Method to add a symbol to a user's watchlist group
    public UserInfoDTO addSymbolToWatchlistGroups(TradeRequest tradeRequest) {
        AddSymbol addSymbol = tradeRequest.getSymbol();
        String symbol = addSymbol.getSymbol();
        Long groupId = addSymbol.getGroupId();

        if (groupId != null) {
            UserInfo userInfo = getCurrentUserInfo();
            Watchlist watchlist = checkWatchlistGroupExists(userInfo, groupId,Constants.Dashboard+Constants.Add_Symbols);

            // Get the symbols list of the watchlist group
            List<String> symbols = watchlist.getSymbols();

            if (symbolRepository.existsBySymbol(symbol)) {

                // Add the symbol to the watchlist group's symbols list
                symbols.add(symbol);

                // Save the changes to UserInfo
                UserInfo updatedUserInfo = userInfoRepository.save(userInfo);

                return convertToUserInfoDTO(updatedUserInfo);
            } else {
                throw new DuplicateEntryException(Constants.Sym_Not_Found,Constants.Dashboard+Constants.Add_Symbols);
            }
        } else {
            throw new ArgumentConstraintViolation(Constants.Grp_Id_Null);
        }
    }


    // Method to get all symbols from a user's watchlist group
    public List<String> getSymbolsFromWatchlistGroups(TradeRequest tradeRequest) {
        UserInfo userInfo = getCurrentUserInfo();
        Long groupId = tradeRequest.getGroupId();
        Watchlist watchlist = checkWatchlistGroupExists(userInfo, groupId,Constants.Dashboard+Constants.Get_Symbols);

        // Get the symbols list of the watchlist group
        List<String> symbols = watchlist.getSymbols();

        return symbols != null ? symbols : Collections.emptyList();
    }


    //Add an Order
    public String addOrder(TradeRequest tradeRequest) {
        Order order = tradeRequest.getOrder();
        String symbol = order.getStockSymbol();
        UserInfo currentUser = getCurrentUserInfo();
        if (symbolRepository.existsBySymbol(symbol)) {
            order.setUserOrdersId(currentUser);
            order.setStatus(Constants.Pending);
            order.setTimestamp(getCurrentTime());
            currentUser.getOrders().add(order);
            userInfoRepository.save(currentUser);
            return Constants.Success;
        } else {
            throw new DuplicateEntryException(Constants.Sym_Not_Found, Constants.Dashboard+Constants.Add_Order);
        }
    }


    //Pause an Existing Order
    public String pauseOrder(TradeRequest tradeRequest) {
        long orderId;
        try {
            orderId = Long.parseLong(tradeRequest.getOrderId());
        } catch (NumberFormatException numberFormatException) {
            throw new CustomException(Constants.Order_Integer, Constants.Dashboard+Constants.Pause_Order_Url);
        }
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (order.getUserOrdersId().getId().equals(getCurrentUserInfo().getId())) {
                order.setStatus(Constants.Paused);
            }else {
                return Constants.No_Privilege;
            }
        }
        return Constants.Pause_Order;
    }


    //Resume a Paused Order
    public String resumeOrder(TradeRequest tradeRequest) {
        long orderId;
        try {
            orderId = Long.parseLong(tradeRequest.getOrderId());
        } catch (NumberFormatException numberFormatException) {
            throw new CustomException(Constants.Order_Integer,Constants.Dashboard+Constants.Resume_Order);
        }
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (order.getUserOrdersId().getId().equals(getCurrentUserInfo().getId())) {
                if (order.getStatus().equals(Constants.Paused)) {
                    order.setStatus(Constants.Pending);
                } else {
                    return Constants.Order_Not_Paused;
                }
            }else {
                return Constants.No_Privilege;
            }
        }
        return Constants.Resume_Order;
    }


    //Cancel an existing Order
    public String cancelOrder(TradeRequest tradeRequest) {

        // Find the order by its ID
        long orderId;
        try {
            orderId = Long.parseLong(tradeRequest.getOrderId());
        } catch (NumberFormatException numberFormatException) {
            throw new CustomException(Constants.Order_Integer,Constants.Dashboard+Constants.Cancel_Order);
        }
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // Check if the user ID of the order matches the current user's ID
            if (order.getUserOrdersId().getId().equals(getCurrentUserInfo().getId())) {

                // Update the order status to CANCELLED
                if(order.getStatus().equals(Constants.Pending)) {
                    order.setStatus(Constants.Cancelled);
                    order.setTimestamp(getCurrentTime());
                }
                else {
                    return Constants.Order_Executed;
                }

                // Save the updated order to the database
                orderRepository.save(order);

                return Constants.Success;
            } else {
                return Constants.No_Privilege;
            }
        } else {
            return Constants.Order_Not_Found;
        }
    }


    //Execute Orders for every 30 Secs
    @Scheduled(fixedRate = 30000)
    public void executeOrders() {
        log.info("Executing orders, method: executeOrders");

        // Retrieve buy and sell orders with matching stock symbols
        List<Order> buyOrders = orderRepository.findBuyOrders();
        List<Order> sellOrders = orderRepository.findSellOrders();

        for (Order buyOrder : buyOrders) {
            for (Order sellOrder : sellOrders) {
                // Check if stock symbols match and buy order price is greater or equal to sell order price
                if (buyOrder.getStockSymbol().equals(sellOrder.getStockSymbol()) &&
                        buyOrder.getPrice() >= sellOrder.getPrice()) {

                    // Check if both buy and sell orders have a status of "PENDING"
                    if (buyOrder.getStatus().equals(Constants.Pending) && sellOrder.getStatus().equals(Constants.Pending)) {

                        // Calculate the transaction quantity
                        int quantity = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());

                        if (quantity > 0) {
                            // Create Trade objects
                            buyOrder.setPrice(sellOrder.getPrice());
                            Trade buyTrade = createTrade(buyOrder, Constants.Buy, quantity);
                            Trade sellTrade = createTrade(sellOrder, Constants.Sell, quantity);

                            // Update user trade history
                            updateUserTradeHistory(buyOrder.getUserOrdersId(), buyTrade);
                            updateUserTradeHistory(sellOrder.getUserOrdersId(), sellTrade);

                            // Update order quantities and statuses
                            updateOrder(buyOrder, quantity);
                            updateOrder(sellOrder, quantity);
                        }
                    }
                }
            }
        }
    }


    //Create a Trade Item
    private Trade createTrade(Order order, String orderType, int quantity) {
        Trade trade = new Trade();
        trade.setUserTradeId(order.getUserOrdersId());
        trade.setStockSymbol(order.getStockSymbol());
        trade.setOrderType(orderType);
        trade.setQuantity(quantity);
        trade.setPrice(order.getPrice());
        trade.setTimestamp(getCurrentTime());
        return trade;
    }


    //Update Trade in Trade table
    private void updateUserTradeHistory(UserInfo user, Trade trade) {
        user.getTrade().add(trade);
        userInfoRepository.save(user);
    }

    //Update Order in orders table
    private void updateOrder(Order order, int quantity) {
        order.setQuantity(order.getQuantity() - quantity);
        if (order.getQuantity() == 0) {
            order.setStatus(Constants.Executed);
        }
        orderRepository.save(order);
    }

    //Get Current User's Portfolio
    public List<PortfolioItemDTO> getPortfolio() {
        List<PortfolioItemDTO> portfolio = new ArrayList<>();

        // Retrieve sell orders and map to PortfolioItemDTO
        portfolio.addAll(mapOrdersToPortfolio(orderRepository.findByUserOrdersIdAndOrderType(getCurrentUserInfo(), Constants.Sell)));

        // Retrieve buy trades and map to PortfolioItemDTO
        portfolio.addAll(mapTradesToPortfolio(tradeRepository.findByUserTradeIdAndOrderType(getCurrentUserInfo(), Constants.Buy)));

        return portfolio;
    }

    //Return Remaining Sell orders of user
    private List<PortfolioItemDTO> mapOrdersToPortfolio(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getQuantity() > 0 && order.getStatus().equals(Constants.Pending))
                .map(order -> createPortfolioItem(order.getStockSymbol(), order.getQuantity(), Constants.Pending, order.getTimestamp()))
                .collect(Collectors.toList());
    }

    //Return executed buy orders of user
    private List<PortfolioItemDTO> mapTradesToPortfolio(List<Trade> trades) {
        return trades.stream()
                .filter(trade -> trade.getQuantity() > 0)
                .map(trade -> createPortfolioItem(trade.getStockSymbol(), trade.getQuantity(), Constants.Executed, trade.getTimestamp()))
                .collect(Collectors.toList());
    }

    //Create a Portfolio Item using PortfolioItemDTO
    private PortfolioItemDTO createPortfolioItem(String stockSymbol, int quantity, String orderStatus, String timestamp) {
        PortfolioItemDTO portfolioItem = new PortfolioItemDTO();
        portfolioItem.setStockSymbol(stockSymbol);
        portfolioItem.setQuantity(quantity);
        portfolioItem.setOrderStatus(orderStatus);
        portfolioItem.setTimestamp(timestamp);
        return portfolioItem;
    }

    //Get Current User's Trade History
    public List<TradeHistoryDTO> getTradeHistory() {
        List<TradeHistoryDTO> tradeHistory = new ArrayList<>();

        // Retrieve orders and map to TradeHistoryDTO
        tradeHistory.addAll(mapOrdersToTradeHistory(orderRepository.findByUserOrdersId(getCurrentUserInfo())));

        // Retrieve trades and map to TradeHistoryDTO
        tradeHistory.addAll(mapTradesToTradeHistory(tradeRepository.findByUserTradeId(getCurrentUserInfo())));

        return tradeHistory;
    }

    //Return all the User's Orders
    private List<TradeHistoryDTO> mapOrdersToTradeHistory(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getQuantity() > 0 && !order.getStatus().equals(Constants.Executed))
                .map(order -> createTradeHistoryItem(order.getStockSymbol(), order.getQuantity(), order.getStatus(), order.getPrice(), order.getOrderType(), order.getTimestamp()))
                .collect(Collectors.toList());
    }

    //Return all the User's Trade
    private List<TradeHistoryDTO> mapTradesToTradeHistory(List<Trade> trades) {
        return trades.stream()
                .map(trade -> createTradeHistoryItem(trade.getStockSymbol(), trade.getQuantity(), Constants.Executed, trade.getPrice(), trade.getOrderType(), trade.getTimestamp()))
                .collect(Collectors.toList());
    }

    //Create a TradeHistoryItem using TradeHistoryDTO
    private TradeHistoryDTO createTradeHistoryItem(String stockSymbol, int quantity, String orderStatus, double price, String orderType, String timestamp) {
        TradeHistoryDTO tradeHistoryItem = new TradeHistoryDTO();
        tradeHistoryItem.setStockSymbol(stockSymbol);
        tradeHistoryItem.setQuantity(quantity);
        tradeHistoryItem.setOrderStatus(orderStatus);
        tradeHistoryItem.setPrice(price);
        tradeHistoryItem.setOrderType(orderType);
        tradeHistoryItem.setTimestamp(timestamp);
        return tradeHistoryItem;
    }


    // Method to convert UserInfo to UserInfoDTO
    private UserInfoDTO convertToUserInfoDTO(UserInfo userInfo) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setId(userInfo.getId());
        userInfoDTO.setUsername(userInfo.getUserName());
        userInfoDTO.setWatchlistGroups(convertToWatchlistGroupDTOs(userInfo.getWatchlists()));
        return userInfoDTO;
    }


    // Method to get all watchlist groups for the current user
    public List<WatchlistGroupDTO> getWatchlistGroups() {
        UserInfo userInfo = getCurrentUserInfo();
        return convertToWatchlistGroupDTOs(userInfo.getWatchlists());
    }


    // Method to convert WatchlistGroups to WatchlistGroupDTOs
    private List<WatchlistGroupDTO> convertToWatchlistGroupDTOs(List<Watchlist> watchlists) {
        return watchlists.stream()
                .map(this::convertToWatchlistGroupDTO)
                .collect(Collectors.toList());
    }


    // Method to convert WatchlistGroup to WatchlistGroupDTO
    private WatchlistGroupDTO convertToWatchlistGroupDTO(Watchlist group) {
        WatchlistGroupDTO groupDTO = new WatchlistGroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setGroupName(group.getGroupName());

        // Set symbols from the watchlist group's symbols list
        List<String> symbols = group.getSymbols();
        groupDTO.setSymbols(symbols != null ? new ArrayList<>(symbols) : Collections.emptyList());

        return groupDTO;
    }
}
