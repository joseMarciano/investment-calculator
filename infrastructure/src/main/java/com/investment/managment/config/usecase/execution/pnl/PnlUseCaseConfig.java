package com.investment.managment.config.usecase.execution.pnl;

import com.investment.managment.execution.calculator.pnl.open.PnLOpenCalculationUseCase;
import com.investment.managment.execution.calculator.pnl.open.totalizator.PnLOpenTotalizatorCalculationUseCase;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.execution.gateway.ExecutionNotification;
import com.investment.managment.stock.gateway.StockGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PnlUseCaseConfig {

    private final StockGateway stockGateway;
    private final ExecutionGateway executionGateway;

    private final ExecutionNotification executionNotification;

    public PnlUseCaseConfig(final StockGateway stockGateway,
                            final ExecutionGateway executionGateway,
                            final ExecutionNotification executionNotification) {
        this.stockGateway = stockGateway;
        this.executionGateway = executionGateway;
        this.executionNotification = executionNotification;
    }

    @Bean
    public PnLOpenCalculationUseCase pnLOpenCalculationUseCase() {
        return new PnLOpenCalculationUseCase(this.stockGateway, this.executionGateway, this.executionNotification);
    }

    @Bean
    public PnLOpenTotalizatorCalculationUseCase pnLOpenTotalizatorCalculationUseCase() {
        return new PnLOpenTotalizatorCalculationUseCase(this.executionGateway);
    }
}
