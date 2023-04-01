package com.investment.managment.execution.sumary;

import com.investment.managment.Identifier;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.WalletID;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
public class ExecutionSummaryID extends Identifier<String> {

    private String value;

    public static ExecutionSummaryID from(final StockID stockId, final WalletID walletId) {
        return new ExecutionSummaryID(stockId, walletId);
    }

    private ExecutionSummaryID(final StockID stockId, final WalletID walletId) {
        this.value = String.format("%s:%s", walletId.getValue(), stockId.getValue());
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ExecutionSummaryID executionID = (ExecutionSummaryID) o;
        return value.equals(executionID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
