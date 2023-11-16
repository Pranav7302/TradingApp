package com.example.Contact.Service;

import com.example.Contact.Dto.*;

import java.util.List;

public interface DashboardService {
    String addWatchlistGroup(TradeRequest tradeRequest);
    UserInfoDTO addSymbolToWatchlistGroups(TradeRequest tradeRequest);
    List<WatchlistGroupDTO> getWatchlistGroups();
    List<String> getSymbolsFromWatchlistGroups(TradeRequest tradeRequest);
    String addOrder(TradeRequest tradeRequest);
    String cancelOrder(TradeRequest tradeRequest);
    List<PortfolioItemDTO> getPortfolio();
    List<TradeHistoryDTO> getTradeHistory();
    String pauseOrder(TradeRequest tradeRequest);
    String resumeOrder(TradeRequest tradeRequest);
}
