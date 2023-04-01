package com.investment.managment.execution.sumary;

import com.investment.managment.AggregateRoot;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.util.Objects.hash;
import static java.util.Optional.ofNullable;

@Builder
@Getter
public class ExecutionSummary extends AggregateRoot<ExecutionSummaryID> {

    private ExecutionSummaryID id;

    private BigDecimal pnlOpen;

    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ExecutionSummary execution = (ExecutionSummary) o;
        return getId().equals(execution.getId());
    }

    @Override
    public int hashCode() {
        return hash(getId());
    }

    public ExecutionSummary resetPnlOpen() {
        this.pnlOpen = ZERO;
        return this;
    }

    public ExecutionSummary updatePnlOpen(final BigDecimal pnlOpen) {
        this.pnlOpen = ofNullable(pnlOpen)
                .orElse(ZERO);
        return this;
    }
}
