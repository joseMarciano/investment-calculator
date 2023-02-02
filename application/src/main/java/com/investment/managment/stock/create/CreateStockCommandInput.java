package com.investment.managment.stock.create;

import com.investment.managment.stock.StockID;

public record CreateStockCommandInput(
        StockID id,
        String symbol
) {

    public static CreateStockCommandInput with(
            final StockID id,
            final String symbol
    ) {
        return new CreateStockCommandInput(id, symbol);
    }
}
