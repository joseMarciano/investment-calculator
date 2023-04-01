package com.investment.managment.execution.calculator.pnl.open.totalizator;

import com.investment.managment.execution.sumary.ExecutionSummaryID;

public record PnLOpenTotalizatorCommandInput(
        ExecutionSummaryID id
) {

    public static PnLOpenTotalizatorCommandInput with(final ExecutionSummaryID anId) {
        return new PnLOpenTotalizatorCommandInput(anId);
    }
}
