package com.investment.managment.execution.calculator.pnl.open.totalizator;

import com.investment.managment.execution.sumary.ExecutionSummaryID;

import java.math.BigDecimal;

public record PnLOpenTotalizatorCommandOutput(
        ExecutionSummaryID id,
        BigDecimal pnl
) {
    public static PnLOpenTotalizatorCommandOutput with(final ExecutionSummaryID anId, final BigDecimal pnl) {
        return new PnLOpenTotalizatorCommandOutput(
                anId,
                pnl
        );
    }
}
