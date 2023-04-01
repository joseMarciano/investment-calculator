package com.investment.managment.stock.model;

import com.investment.managment.stock.StockID;

import java.math.BigDecimal;

public record LastTradePriceRequest(
        String id,
        String symbol,
        BigDecimal lastTradePrice
) {
    public static LastTradePriceRequest with(final StockID id,
                                             final String symbol,
                                             final BigDecimal lastTradePrice) {
        return new LastTradePriceRequest(
                id.getValue(),
                symbol,
                lastTradePrice
        );
    }
}
