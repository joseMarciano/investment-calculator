package com.investment.managment.api.stock.scheduler;

public interface StockAPIScheduleTasks {
    void updateOrCreateStocks();
    void updateLastTradePrice();
    void verifyUpdateUsedStocks();
}
