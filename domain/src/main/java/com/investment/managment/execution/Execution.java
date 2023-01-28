package com.investment.managment.execution;

import com.investment.managment.AggregateRoot;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.WalletID;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Builder
@Getter
public class Execution extends AggregateRoot<ExecutionID> {

    private ExecutionID id;

    private ExecutionID origin;

    private StockID stockId;

    private WalletID walletId;

    private Double profitPercentage;

    private Long executedQuantity;

    private BigDecimal executedPrice;

    private BigDecimal executedVolume;

    private ExecutionStatus status;

    @Override
    public ExecutionID getId() {
        return this.id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Execution execution = (Execution) o;
        return getId().equals(execution.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
