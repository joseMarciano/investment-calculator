package com.investment.managment.stock.create;

import com.investment.managment.UseCase;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.gateway.StockGateway;

import static java.util.Objects.requireNonNull;

public class CreateStockUseCase extends UseCase<CreateStockCommandInput, CreateStockCommandOutput> {

    private final StockGateway stockGateway;

    public CreateStockUseCase(final StockGateway stockGateway) {
        this.stockGateway = requireNonNull(stockGateway);
    }

    @Override
    public CreateStockCommandOutput execute(final CreateStockCommandInput aCommand) {
        final var aStock = Stock.builder()
                .id(aCommand.id())
                .symbol(aCommand.symbol())
                .build();

        return CreateStockCommandOutput.from(stockGateway.create(aStock));
    }
}
