package com.investment.managment.execution.update;

import com.investment.managment.UseCase;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.gateway.ExecutionGateway;

import static java.util.Objects.requireNonNull;

public class UpdateExecutionUseCase extends UseCase<UpdateExecutionCommandInput, UpdateExecutionCommandOutput> {

    private final ExecutionGateway executionGateway;

    public UpdateExecutionUseCase(final ExecutionGateway executionGateway) {
        this.executionGateway = requireNonNull(executionGateway);
    }

    @Override
    public UpdateExecutionCommandOutput execute(final UpdateExecutionCommandInput aCommand) {
        final var aExecution = Execution.builder()
                .id(aCommand.id())
                .origin(aCommand.origin())
                .stockId(aCommand.stockId())
                .walletId(aCommand.walletId())
                .profitPercentage(aCommand.profitPercentage())
                .executedQuantity(aCommand.executedQuantity())
                .executedPrice(aCommand.executedPrice())
                .executedVolume(aCommand.executedVolume())
                .status(aCommand.status())
                .executionsSold(aCommand.executionsSold())
                .build();

        return UpdateExecutionCommandOutput.from(executionGateway.update(aExecution));
    }
}
