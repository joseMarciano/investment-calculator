package com.investment.managment.execution.calculator.pnl.open;

import com.investment.managment.UseCase;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.execution.gateway.ExecutionNotification;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.gateway.StockGateway;
import com.investment.managment.validation.exception.DomainExeceptionFactory;

import java.util.Optional;

import static java.math.BigDecimal.ZERO;
import static java.util.Optional.ofNullable;

public class PnLOpenCalculationUseCase extends UseCase<PnLOpenCommandInput, PnLOpenCommandOutput> {

    private final StockGateway stockGateway;
    private final ExecutionGateway executionGateway;
    private final ExecutionNotification executionNotification;

    public PnLOpenCalculationUseCase(final StockGateway stockGateway,
                                     final ExecutionGateway executionGateway,
                                     final ExecutionNotification executionNotification) {
        this.stockGateway = stockGateway;
        this.executionGateway = executionGateway;
        this.executionNotification = executionNotification;
    }

    @Override
    public PnLOpenCommandOutput execute(final PnLOpenCommandInput input) {
        return this.executionGateway.findById(input.id())
                .map(this::calculate)
                .orElse(PnLOpenCommandOutput.with(input.id(), ZERO));
    }

    private PnLOpenCommandOutput calculate(final Execution execution) {
        final var aStock = getStock(execution);
        final var lastTradePrice = aStock.getLastTradePrice();
        final var oldPnlOpen = ofNullable(execution.getPnlOpen()).orElse(ZERO);

        final var executionsSold = execution.getExecutionsSold().stream()
                .map(executionGateway::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        execution.calculatePnlOpen(lastTradePrice, executionsSold);

        final var executionUpdated = executionGateway
                .update(execution);

        final var newPnlOpen = ofNullable(execution.getPnlOpen()).orElse(ZERO);
        final var pnlChanged = newPnlOpen.compareTo(oldPnlOpen) != 0;

        if (pnlChanged) executionNotification.notifyPnlOpen(executionUpdated);

        return PnLOpenCommandOutput.with(
                execution.getId(),
                executionUpdated.getPnlOpen()
        );
    }

    private Stock getStock(final Execution execution) {
        final var aStockId = execution.getStockId();
        return this.stockGateway.findById(aStockId)
                .orElseThrow(() -> DomainExeceptionFactory.notFoundException(aStockId, Stock.class));
    }
}
