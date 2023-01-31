package com.investment.managment.stock;

import com.investment.managment.Identifier;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
public class StockID extends Identifier<String> {

    private String value;

    public static StockID from(final String aValue) {
        return new StockID(aValue);
    }

    private StockID(final String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final StockID stockID = (StockID) o;
        return value.equals(stockID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
