package com.investment.managment.execution;

import com.investment.managment.AggregateRoot;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.WalletID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.hash;
import static java.util.Objects.isNull;

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

    @Setter(AccessLevel.NONE)
    private Set<ExecutionID> executionsSold;

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
        return hash(getId());
    }

    public BigDecimal calculatePnlOpen(final BigDecimal lastTradePrice) {
        if (ExecutionStatus.SELL.equals(this.getStatus())) return BigDecimal.ZERO;
        // todo: implementar websocket
        final var executedQuantity = BigDecimal.valueOf(this.executedQuantity);
        return executedQuantity.multiply(
                lastTradePrice.subtract(this.executedPrice)
        );
    }

    public BigDecimal calculatePnlClose(final BigDecimal lastTradePrice) {
        if (ExecutionStatus.BUY.equals(this.getStatus())) return BigDecimal.ZERO;

        final var executedQuantity = BigDecimal.valueOf(this.executedQuantity);
        return executedQuantity.multiply(
                this.executedPrice.subtract(lastTradePrice)
        );
    }

    public void addExecutionSold(final ExecutionID anId) {
        if (isNull(this.executionsSold)) {
            this.executionsSold = new HashSet<>();
        }

        this.executionsSold.add(anId);
    }

    public void removeExecutionSold(final ExecutionID anId) {
        if (isNull(this.executionsSold)) return;

        this.executionsSold.remove(anId);
    }
}
