package com.investment.managment.execution.calculator.pnl.open.totalizator;

import com.investment.managment.UseCase;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.execution.sumary.ExecutionSummaryID;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Predicate;

import static java.math.BigDecimal.ZERO;

public class PnLOpenTotalizatorCalculationUseCase extends UseCase<PnLOpenTotalizatorCommandInput, PnLOpenTotalizatorCommandOutput> {

    private final ExecutionGateway executionGateway;

    public PnLOpenTotalizatorCalculationUseCase(final ExecutionGateway executionGateway) {
        this.executionGateway = executionGateway;
    }

    @Override
    public PnLOpenTotalizatorCommandOutput execute(final PnLOpenTotalizatorCommandInput input) {
        final var pnlOpenTotal = this.executionGateway.findAll()
                .stream()
                .filter(filterByWalletAndStock(input))
                .map(Execution::getPnlOpen)
                .filter(Objects::nonNull)
                .reduce(BigDecimal::add)
                .orElse(ZERO);

        final var executionSummary = this.executionGateway.getOrCreateExecutionSummary(input.id());

        executionSummary.updatePnlOpen(pnlOpenTotal);
        this.executionGateway.updateExecutionSummary(executionSummary);

        return PnLOpenTotalizatorCommandOutput.with(executionSummary.getId(), executionSummary.getPnlOpen());
    }

    private Predicate<Execution> filterByWalletAndStock(PnLOpenTotalizatorCommandInput input) {
        return execution -> ExecutionSummaryID.from(execution.getStockId(), execution.getWalletId()).equals(input.id());
    }

}
