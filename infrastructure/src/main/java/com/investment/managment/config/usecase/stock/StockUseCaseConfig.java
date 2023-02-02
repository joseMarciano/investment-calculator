package com.investment.managment.config.usecase.stock;

import com.investment.managment.stock.create.CreateStockUseCase;
import com.investment.managment.stock.gateway.StockGateway;
import com.investment.managment.stock.update.UpdateStockUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockUseCaseConfig {

    private final StockGateway stockGateway;

    public StockUseCaseConfig(final StockGateway stockGateway) {
        this.stockGateway = stockGateway;
    }

    @Bean
    public CreateStockUseCase createStockUseCase() {
        return new CreateStockUseCase(this.stockGateway);
    }

    @Bean
    public UpdateStockUseCase updateStockUseCase() {
        return new UpdateStockUseCase(this.stockGateway);
    }

}
