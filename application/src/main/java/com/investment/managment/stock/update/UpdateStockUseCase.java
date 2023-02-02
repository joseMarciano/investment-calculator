package com.investment.managment.stock.update;

import com.investment.managment.UseCase;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockID;
import com.investment.managment.stock.gateway.StockGateway;
import com.investment.managment.validation.exception.DomainExeceptionFactory;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class UpdateStockUseCase extends UseCase<UpdateStockCommandInput, UpdateStockCommandOutput> {

    private final StockGateway stockGateway;

    public UpdateStockUseCase(final StockGateway stockGateway) {
        this.stockGateway = requireNonNull(stockGateway);
    }

    @Override
    public UpdateStockCommandOutput execute(final UpdateStockCommandInput aCommand) {
        final var anId = aCommand.id();
        return this.stockGateway.findById(anId)
                .map(update(aCommand))
                .orElseThrow(() -> DomainExeceptionFactory.notFoundException(anId, Stock.class));
    }

    public Function<Stock, UpdateStockCommandOutput> update(final UpdateStockCommandInput aCommand) {
        return stock -> UpdateStockCommandOutput.from(stockGateway.update(stock.update(aCommand.symbol(), aCommand.lastTradePrice())));
    }
}
