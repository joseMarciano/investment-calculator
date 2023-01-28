package com.investment.managment.execution.create;

import com.investment.managment.UseCase;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.gateway.ExecutionGateway;

import static java.util.Objects.requireNonNull;

public class CreateExecutionUseCase extends UseCase<CreateExecutionCommandInput, CreateExecutionCommandOutput> {

    private final ExecutionGateway executionGateway;

    public CreateExecutionUseCase(final ExecutionGateway executionGateway) {
        this.executionGateway = requireNonNull(executionGateway);
    }

    @Override
    public CreateExecutionCommandOutput execute(final CreateExecutionCommandInput aCommand) {
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
                .build();

        return CreateExecutionCommandOutput.from(executionGateway.create(aExecution));
    }
}
