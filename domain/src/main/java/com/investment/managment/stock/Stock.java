package com.investment.managment.stock;

import com.investment.managment.AggregateRoot;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Builder
@Getter
public class Stock extends AggregateRoot<StockID> {

    private StockID id;

    private String symbol;

    private BigDecimal lastTradePrice;

    @Override
    public StockID getId() {
        return this.id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Stock stock = (Stock) o;
        return getId().equals(stock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public BigDecimal getLastTradePrice() {
        return Objects.nonNull(lastTradePrice) ? lastTradePrice : BigDecimal.ZERO;
    }

    public Stock update(final String symbol, final BigDecimal lastTradePrice) {
        this.symbol = symbol;
        this.lastTradePrice = lastTradePrice;
        return this;
    }
}
