package com.investment.managment.api.websocket;

import com.investment.managment.UseCase;
import com.investment.managment.application.execution.calculator.pnl.PnlOpenCalculationWebSocketAdapter;
import com.investment.managment.config.context.ApplicationContext;

public enum WebSocketRequesType {
    PNL(PnlOpenCalculationWebSocketAdapter.class);

    private final Class<?> clazz;

    WebSocketRequesType(final Class<? extends UseCase> clazz) {
        this.clazz = clazz;
    }

    public UseCase<Object, ?> getUseCase() {
        return (UseCase<Object, ?>) ApplicationContext.getBean(this.clazz);
    }
}
