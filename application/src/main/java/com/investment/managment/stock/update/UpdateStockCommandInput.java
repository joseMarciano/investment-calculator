package com.investment.managment.stock.update;

import com.investment.managment.stock.StockID;

import java.math.BigDecimal;

public record UpdateStockCommandInput(
        StockID id,
        String symbol,
        BigDecimal lastTradePrice
) {

    public static UpdateStockCommandInput with(
            final StockID id,
            final String symbol,
            final BigDecimal lastTradePrice
    ) {
        return new UpdateStockCommandInput(id, symbol, lastTradePrice);
    }
}
