package com.investment.managment.application.execution.calculator.pnl;

import com.investment.managment.UseCase;
import com.investment.managment.execution.calculator.pnl.open.PnLOpenCalculationUseCase;
import com.investment.managment.execution.calculator.pnl.open.PnLOpenCommandInput;
import com.investment.managment.execution.calculator.pnl.open.PnLOpenCommandOutput;
import com.investment.managment.execution.presenter.ExecutionApiPresenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class PnlOpenCalculationWebSocketAdapter extends UseCase<PnLOpenCommandInput, PnLOpenCommandOutput> {

    private final PnLOpenCalculationUseCase pnLOpenCalculationUseCase;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final String userId;

    public PnlOpenCalculationWebSocketAdapter(final PnLOpenCalculationUseCase pnLOpenCalculationUseCase,
                                              final SimpMessagingTemplate simpMessagingTemplate,
                                              final @Value("${user-id}") String userId) {
        this.pnLOpenCalculationUseCase = pnLOpenCalculationUseCase;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userId = userId;
    }

    @Override
    public PnLOpenCommandOutput execute(final PnLOpenCommandInput input) {
        final PnLOpenCommandOutput output = this.pnLOpenCalculationUseCase.execute(input);
        simpMessagingTemplate.convertAndSendToUser(this.userId, "pnl-open", ExecutionApiPresenter.present.apply(output));
        return output;
    }
}
