package com.investment.managment.execution.presenter;

import com.investment.managment.execution.calculator.pnl.open.PnLOpenCommandOutput;
import com.investment.managment.execution.models.pnl.PnlOpenResponse;

public interface ExecutionApiPresenter {

    static PnlOpenResponse present(final PnLOpenCommandOutput anOutput) {
        return new PnlOpenResponse(anOutput.id().getValue(), anOutput.pnl());
    }

}
