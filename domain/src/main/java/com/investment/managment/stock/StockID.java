package com.investment.managment.stock;

import com.investment.managment.Identifier;

import java.util.Objects;

public class StockID extends Identifier<String> {

    private final String id;

    public static StockID from(final String aValue) {
        return new StockID(aValue);
    }

    private StockID(final String id) {
        this.id = id;
    }

    @Override
    public String getValue() {
        return this.id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final StockID stockID = (StockID) o;
        return id.equals(stockID.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
