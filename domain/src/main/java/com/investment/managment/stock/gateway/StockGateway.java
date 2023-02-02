package com.investment.managment.stock.gateway;

import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockID;
import com.investment.managment.stock.StockUsed;

import java.util.Optional;
import java.util.Set;

public interface StockGateway {

    Stock create(Stock aStock);

    Stock update(Stock aStock);

    Optional<Stock> findById(StockID aStockID);

    Set<StockUsed> findUsedStocks();

    void clearAllStockUsed();

    void addStockUsed(Set<StockUsed> aStockID);

}
