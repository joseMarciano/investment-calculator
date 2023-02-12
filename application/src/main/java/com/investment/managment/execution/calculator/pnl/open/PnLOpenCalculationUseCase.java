package com.investment.managment.execution.calculator.pnl.open;

import com.investment.managment.UseCase;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.gateway.StockGateway;
import com.investment.managment.validation.exception.DomainExeceptionFactory;

import java.math.BigDecimal;

public class PnLOpenCalculationUseCase extends UseCase<PnLOpenCommandInput, PnLOpenCommandOutput> {

    private final StockGateway stockGateway;
    private final ExecutionGateway executionGateway;

    public PnLOpenCalculationUseCase(final StockGateway stockGateway,
                                     final ExecutionGateway executionGateway) {
        this.stockGateway = stockGateway;
        this.executionGateway = executionGateway;
    }

    @Override
    public PnLOpenCommandOutput execute(final PnLOpenCommandInput input) {
        return this.executionGateway.findById(input.id())
                .map(this::calculate)
                .orElse(PnLOpenCommandOutput.with(null, BigDecimal.ZERO));
    }

    private PnLOpenCommandOutput calculate(final Execution execution) {
        final var aStock = getStock(execution);
        final var lastTradePrice = aStock.getLastTradePrice();

        return PnLOpenCommandOutput.with(
                execution.getId(),
                execution.calculatePnlOpen(lastTradePrice)
        );
    }

    private Stock getStock(final Execution execution) {
        final var aStockId = execution.getStockId();
        return this.stockGateway.findById(aStockId)
                .orElseThrow(() -> DomainExeceptionFactory.notFoundException(aStockId, Stock.class));
    }
}
