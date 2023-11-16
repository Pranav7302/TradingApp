package com.example.Contact.Controller;

import com.example.Contact.Constants.Constants;
import com.example.Contact.Constants.HelperMethod;
import com.example.Contact.Dto.*;
import com.example.Contact.Service.DashboardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Log4j2
@RequestMapping(Constants.Dashboard)
@RestController
public class DashboardController {
    @Autowired
    DashboardService dashboardService;

    @PostMapping(Constants.Add_Groups)        //Adding watchlist group to the user's watchlist
    public TradeResponse addWatchlistGroup(@RequestBody TradeRequest tradeRequest)
    {
        String addWatchlistGroup =  dashboardService.addWatchlistGroup(tradeRequest);
        log.info("Added Watchlist group, method : addWatchlistGroup ");
        return HelperMethod.createResponse("watchlistGroupAdded ", addWatchlistGroup);
    }


    @PostMapping(Constants.Add_Symbols)        //Adding symbols to user's watchlist group
    public TradeResponse addSymbol(@RequestBody TradeRequest tradeRequest)
    {
        UserInfoDTO userInfo = dashboardService.addSymbolToWatchlistGroups(tradeRequest);
        log.info("Added Symbol to Watchlist group, method : addSymbol ");
        return HelperMethod.createResponse("symbolAddedToWatchlistGroup", userInfo);
    }
    @PostMapping(Constants.Add_Order)   // Adding order
    public TradeResponse addOrder(@RequestBody TradeRequest tradeRequest)
    {
        String response = dashboardService.addOrder(tradeRequest);
        log.info("Order Added, method : addOrder ");
        return HelperMethod.createResponse("addOrder",response);
    }

    @PostMapping(Constants.Pause_Order_Url)   // Pausing an order
    public TradeResponse pauseOrder(@RequestBody TradeRequest tradeRequest)
    {
        String response = dashboardService.pauseOrder(tradeRequest);
        log.info("Order Paused, method : addOrder ");
        return HelperMethod.createResponse("addOrder",response);
    }

    @PostMapping(Constants.Resume_Order_Url)   // Resuming an order
    public TradeResponse resumeOrder(@RequestBody TradeRequest tradeRequest)
    {
        String response = dashboardService.resumeOrder(tradeRequest);
        log.info("Order Resumed, method : addOrder ");
        return HelperMethod.createResponse("addOrder",response);
    }

    @PostMapping(Constants.Cancel_Order)  //cancel an existing order
    public TradeResponse cancelOrder(@RequestBody TradeRequest tradeRequest)
    {
        String response = dashboardService.cancelOrder(tradeRequest);
        log.info("Cancelled Order, method : cancelOrder ");
        return HelperMethod.createResponse("cancelOrder",response);
    }


    @GetMapping(Constants.Get_Portfolio)  //get user's Portfolio
    public TradeResponse getPortfolio() {
        List<PortfolioItemDTO> portfolio = dashboardService.getPortfolio();
        log.info("Retrieved portfolio, method : getPortfolio ");
        return HelperMethod.createResponse("getPortfolio", portfolio);
    }


    @GetMapping(Constants.Get_Trade_History)   //get User's Trade History
    public TradeResponse getTradeHistory() {
        List<TradeHistoryDTO> tradeHistory = dashboardService.getTradeHistory();
        log.info("Retrieved tradeHistory, method : getTradeHistory ");
        return HelperMethod.createResponse("getTradeHistory", tradeHistory);
    }

    @GetMapping(Constants.Get_Watchlist_Groups)         //Get all the user's watchlist groups
    public TradeResponse getWatchlistGroups() {
        List<WatchlistGroupDTO> watchlistGroups = dashboardService.getWatchlistGroups();
        log.info("Retrieved groups from watchlist, method : getWatchlistGroups ");
        return HelperMethod.createResponse("watchlistGroups", watchlistGroups);
    }


    @GetMapping(Constants.Get_Watchlist_Symbols)     //get all the symbols of a user's watchlist group based on group ID
    public TradeResponse getSymbols(@RequestBody TradeRequest tradeRequest) {
        List<String> symbols = dashboardService.getSymbolsFromWatchlistGroups(tradeRequest);
        log.info("Retrieved symbols from watchlist, method : getSymbols ");
        return HelperMethod.createResponse("symbols", symbols);
    }
}
