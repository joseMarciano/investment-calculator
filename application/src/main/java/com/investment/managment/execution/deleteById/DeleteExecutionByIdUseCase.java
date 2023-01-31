package com.investment.managment.execution.deleteById;

import com.investment.managment.UnitUseCase;
import com.investment.managment.UseCase;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.gateway.ExecutionGateway;

import static java.util.Objects.requireNonNull;

public class DeleteExecutionByIdUseCase extends UnitUseCase<DeleteExecutionByIdCommandInput> {

    private final ExecutionGateway executionGateway;

    public DeleteExecutionByIdUseCase(final ExecutionGateway executionGateway) {
        this.executionGateway = requireNonNull(executionGateway);
    }

    @Override
    public void execute(final DeleteExecutionByIdCommandInput aCommand) {
        this.executionGateway.deleteById(aCommand.id());
    }

    private void delete(final Execution execution) {
        this.executionGateway.deleteById(execution.getId());
    }
}
