package com.investment.managment.execution.presenter;

import com.investment.managment.execution.calculator.pnl.open.PnLOpenCommandOutput;
import com.investment.managment.execution.calculator.pnl.open.totalizator.PnLOpenTotalizatorCommandOutput;
import com.investment.managment.execution.models.pnl.PnlOpenResponse;
import com.investment.managment.execution.models.pnl.PnlOpenTotalizatorResponse;
import com.investment.managment.execution.models.pnl.PnlOpenWebSocketResponse;

import java.util.function.Function;

public interface ExecutionApiPresenter {

    static PnlOpenResponse present(final PnLOpenCommandOutput anOutput) {
        return new PnlOpenResponse(anOutput.id().getValue(), anOutput.pnl());
    }

    static PnlOpenTotalizatorResponse present(final PnLOpenTotalizatorCommandOutput anOutput) {
        return new PnlOpenTotalizatorResponse(anOutput.id().getValue(), anOutput.pnl());
    }

    Function<PnLOpenCommandOutput, PnlOpenWebSocketResponse> present =
            pnLOpenCommandOutput -> new PnlOpenWebSocketResponse(pnLOpenCommandOutput.id().getValue(), pnLOpenCommandOutput.pnl());

}
