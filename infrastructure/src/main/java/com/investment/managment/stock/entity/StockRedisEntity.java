package com.investment.managment.stock.entity;

import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;

@RedisHash("Stock")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class StockRedisEntity {

    @Id
    private String id;

    private String symbol;

    private BigDecimal lastTradePrice;

    public static StockRedisEntity from(final Stock stock) {
        return builder()
                .id(stock.getId().getValue())
                .symbol(stock.getSymbol())
                .lastTradePrice(stock.getLastTradePrice())
                .build();
    }

    public Stock toAggregate() {
        return Stock.builder()
                .id(StockID.from(this.id))
                .symbol(this.symbol)
                .lastTradePrice(this.lastTradePrice)
                .build();
    }
}
