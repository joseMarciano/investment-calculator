package com.investment.managment.http.getstockprice;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public record GetStockPriceResponse(Object results) {

    public List<StockLastTradePrice> buildItems() {
        try {
            return ((LinkedHashMap<String, Object>) results)
                    .values()
                    .stream()
                    .map(this::buildStockPriceResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error on getItems {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private StockLastTradePrice buildStockPriceResponse(final Object obj) {
        final var map = (LinkedHashMap<String, Object>) obj;
        final var symbol = (String) map.get("symbol");

        if(Objects.isNull(symbol)) return null;

        final var price = Optional.ofNullable((Double) map.get("price")).map(BigDecimal::valueOf).orElse(BigDecimal.ZERO);

        return new StockLastTradePrice(symbol, price);
    }
}
