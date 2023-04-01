package com.investment.managment.application.execution.calculator.pnl;

import com.investment.managment.execution.calculator.pnl.open.totalizator.PnLOpenTotalizatorCalculationUseCase;
import com.investment.managment.execution.calculator.pnl.open.totalizator.PnLOpenTotalizatorCommandInput;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.execution.presenter.ExecutionApiPresenter;
import com.investment.managment.execution.sumary.ExecutionSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class PnlOpenTotalizatorCalculationWebSocketAdapter {

    private final PnLOpenTotalizatorCalculationUseCase pnLOpenTotalizerCalculationUseCase;
    private final ExecutionGateway executionGateway;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final String userId;

    public PnlOpenTotalizatorCalculationWebSocketAdapter(final PnLOpenTotalizatorCalculationUseCase pnLOpenTotalizerCalculationUseCase,
                                                         final ExecutionGateway executionGateway,
                                                         final SimpMessagingTemplate simpMessagingTemplate,
                                                         final @Value("${user-id}") String userId) {
        this.pnLOpenTotalizerCalculationUseCase = pnLOpenTotalizerCalculationUseCase;
        this.executionGateway = executionGateway;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userId = userId;
    }

    public void execute() {
        this.executionGateway.findAllExecutionSummary()
                .forEach(resolve());
    }

    private Consumer<ExecutionSummary> resolve() {
        return executionSummary -> {
            final var output = this.pnLOpenTotalizerCalculationUseCase.execute(PnLOpenTotalizatorCommandInput.with(executionSummary.getId()));
            final var walletId = executionSummary.getId().getValue().split(":")[0];
            final var stockId = executionSummary.getId().getValue().split(":")[1];
            simpMessagingTemplate.convertAndSendToUser(this.userId, walletId + "/" + stockId + "/pnl-open-totalizator", ExecutionApiPresenter.present(output));
        };
    }

}
