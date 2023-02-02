package com.investment.managment.stock.update;

import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockID;

import java.math.BigDecimal;

public record UpdateStockCommandOutput(
        StockID id,
        String symbol,
        BigDecimal lastTradePrice
) {
    public static UpdateStockCommandOutput from(final Stock aStock) {
        return new UpdateStockCommandOutput(
                aStock.getId(),
                aStock.getSymbol(),
                aStock.getLastTradePrice()
        );
    }
}
