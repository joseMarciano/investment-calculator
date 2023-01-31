package com.investment.managment.config.usecase.execution;

import com.investment.managment.execution.create.CreateExecutionUseCase;
import com.investment.managment.execution.deleteById.DeleteExecutionByIdUseCase;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.execution.update.UpdateExecutionUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutionUseCaseConfig {

    private final ExecutionGateway executionGateway;

    public ExecutionUseCaseConfig(final ExecutionGateway executionGateway) {
        this.executionGateway = executionGateway;
    }

    @Bean
    public CreateExecutionUseCase createExecutionUseCase(){
        return new CreateExecutionUseCase(this.executionGateway);
    }

    @Bean
    public UpdateExecutionUseCase updateExecutionUseCase(){
        return new UpdateExecutionUseCase(this.executionGateway);
    }

    @Bean
    public DeleteExecutionByIdUseCase deleteExecutionByIdUseCase(){
        return new DeleteExecutionByIdUseCase(this.executionGateway);
    }

}
