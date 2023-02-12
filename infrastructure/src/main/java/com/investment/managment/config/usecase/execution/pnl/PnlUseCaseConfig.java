package com.investment.managment.config.usecase.execution.pnl;

import com.investment.managment.execution.calculator.pnl.open.PnLOpenCalculationUseCase;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.stock.gateway.StockGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PnlUseCaseConfig {

    private final StockGateway stockGateway;
    private final ExecutionGateway executionGateway;

    public PnlUseCaseConfig(final StockGateway stockGateway, final ExecutionGateway executionGateway) {
        this.stockGateway = stockGateway;
        this.executionGateway = executionGateway;
    }

    @Bean
    public PnLOpenCalculationUseCase pnLOpenCalculationUseCase() {
        return new PnLOpenCalculationUseCase(this.stockGateway, this.executionGateway);
    }
}
