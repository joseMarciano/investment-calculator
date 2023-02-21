package com.investment.managment.application.execution.calculator.pnl;

import com.investment.managment.UseCase;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.calculator.pnl.open.PnLOpenCalculationUseCase;
import com.investment.managment.execution.calculator.pnl.open.PnLOpenCommandInput;
import com.investment.managment.execution.calculator.pnl.open.PnLOpenCommandOutput;
import com.investment.managment.execution.models.pnl.PnlOpenWebSocketRequest;
import com.investment.managment.execution.presenter.ExecutionApiPresenter;
import com.investment.managment.utils.Json;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PnlOpenCalculationWebSocketAdapter extends UseCase<String, PnLOpenCommandOutput> {

    private final PnLOpenCalculationUseCase pnLOpenCalculationUseCase;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public PnlOpenCalculationWebSocketAdapter(final PnLOpenCalculationUseCase pnLOpenCalculationUseCase,
                                              final SimpMessagingTemplate simpMessagingTemplate) {
        this.pnLOpenCalculationUseCase = pnLOpenCalculationUseCase;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Async
    @Override
    public PnLOpenCommandOutput execute(final String input) {
        System.out.println(Thread.currentThread().getName());
        final var request = Json.convertToObj(input, PnlOpenWebSocketRequest.class);
        final PnLOpenCommandOutput output = this.pnLOpenCalculationUseCase.execute(PnLOpenCommandInput.with(ExecutionID.from(request.getExecutionID())));
        simpMessagingTemplate.convertAndSendToUser(request.getExecutionID(), "pnl", ExecutionApiPresenter.present.apply(output));
        return output;
    }
}
