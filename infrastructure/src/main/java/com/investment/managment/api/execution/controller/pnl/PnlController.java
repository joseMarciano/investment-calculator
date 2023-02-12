package com.investment.managment.api.execution.controller.pnl;

import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.calculator.pnl.open.PnLOpenCalculationUseCase;
import com.investment.managment.execution.calculator.pnl.open.PnLOpenCommandInput;
import com.investment.managment.execution.models.pnl.PnlOpenResponse;
import com.investment.managment.execution.presenter.ExecutionApiPresenter;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PnlController implements PnlAPI {

    private final PnLOpenCalculationUseCase pnLOpenCalculationUseCase;

    public PnlController(final PnLOpenCalculationUseCase pnLOpenCalculationUseCase) {
        this.pnLOpenCalculationUseCase = pnLOpenCalculationUseCase;
    }

    @Override
    public PnlOpenResponse getPnlOpen(final String executionId) {
        final var anInput = PnLOpenCommandInput.with(ExecutionID.from(executionId));
        return ExecutionApiPresenter.present(this.pnLOpenCalculationUseCase.execute(anInput));
    }
}
