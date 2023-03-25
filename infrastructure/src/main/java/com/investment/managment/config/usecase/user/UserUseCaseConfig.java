package com.investment.managment.config.usecase.user;

import com.investment.managment.stock.create.CreateStockUseCase;
import com.investment.managment.stock.update.UpdateStockUseCase;
import com.investment.managment.user.UserGateway;
import com.investment.managment.user.online.UserOnlineUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCaseConfig {

    private final UserGateway userGateway;

    public UserUseCaseConfig(final UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Bean
    public UserOnlineUseCase userOnlineUseCase() {
        return new UserOnlineUseCase(this.userGateway);
    }
}
