package com.investment.managment.http.getstockprice;

import java.math.BigDecimal;

public record StockLastTradePrice(
        String symbol,
        BigDecimal lastTradePrice
) {
}
