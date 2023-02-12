package com.investment.managment.execution.calculator.pnl.open;

import com.investment.managment.execution.ExecutionID;

public record PnLOpenCommandInput(
        ExecutionID id
) {

    public static PnLOpenCommandInput with(final ExecutionID anId) {
        return new PnLOpenCommandInput(anId);
    }
}
