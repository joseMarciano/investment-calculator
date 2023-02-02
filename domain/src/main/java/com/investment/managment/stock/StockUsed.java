package com.investment.managment.stock;

import com.investment.managment.AggregateRoot;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Builder
@Getter
public class StockUsed extends AggregateRoot<StockID> {

    private StockID id;

    private String symbol;
    @Override
    public StockID getId() {
        return this.id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final StockUsed stock = (StockUsed) o;
        return getId().equals(stock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
