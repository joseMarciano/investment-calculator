package com.investment.managment.stock.entity;

import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockID;
import com.investment.managment.stock.StockUsed;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;

@RedisHash("Stock-Used")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class StockUsedRedisEntity {

    @Id
    private String id;

    private String symbol;

    public static StockUsedRedisEntity from(final StockUsed stockUsed) {
        return builder()
                .id(stockUsed.getId().getValue())
                .symbol(stockUsed.getSymbol())
                .build();
    }

    public StockUsed toAggregate() {
        return StockUsed.builder()
                .id(StockID.from(this.id))
                .symbol(this.symbol)
                .build();
    }
}
