package com.investment.managment.stock.create;

import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockID;

import java.math.BigDecimal;

public record CreateStockCommandOutput(
        StockID id,
        String symbol,
        BigDecimal lastTradePrice
) {
    public static CreateStockCommandOutput from(final Stock aStock) {
        return new CreateStockCommandOutput(
                aStock.getId(),
                aStock.getSymbol(),
                aStock.getLastTradePrice()
        );
    }
}
