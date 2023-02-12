package com.investment.managment.execution.calculator.pnl.open;

import com.investment.managment.execution.ExecutionID;

import java.math.BigDecimal;

public record PnLOpenCommandOutput(
        ExecutionID id,
        BigDecimal pnl
) {
    public static PnLOpenCommandOutput with(final ExecutionID anId, final BigDecimal pnl) {
        return new PnLOpenCommandOutput(
                anId,
                pnl
        );
    }
}
