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
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
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

    private BigDecimal pnlOpen;

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

    public Execution calculatePnlOpen(final BigDecimal lastTradePrice, final List<Execution> executionsSold) {
        if (ExecutionStatus.SELL.equals(this.getStatus())) {
            this.pnlOpen = ZERO;
            return this;
        }

        final var soldQuantity = executionsSold.stream()
                .map(Execution::getExecutedQuantity)
                .filter(Objects::nonNull)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal::add)
                .orElse(ZERO);

        final var executedQuantity = valueOf(this.executedQuantity);

        this.pnlOpen = executedQuantity
                .subtract(soldQuantity)
                .multiply(lastTradePrice.subtract(this.executedPrice));

        return this;
    }

    public BigDecimal calculatePnlClose(final BigDecimal lastTradePrice) {
        if (ExecutionStatus.BUY.equals(this.getStatus())) return ZERO;

        final var executedQuantity = valueOf(this.executedQuantity);
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
