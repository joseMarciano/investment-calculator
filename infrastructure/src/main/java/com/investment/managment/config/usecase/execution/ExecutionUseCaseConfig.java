package com.investment.managment.config.usecase.execution;

import com.investment.managment.execution.create.CreateExecutionUseCase;
import com.investment.managment.execution.gateway.ExecutionGateway;
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

}
