package com.investment.managment.api.listener;

import com.investment.managment.execution.ExecutionChangeReason;
import com.investment.managment.execution.create.CreateExecutionCommandInput;
import com.investment.managment.execution.create.CreateExecutionUseCase;
import com.investment.managment.execution.models.ExecutionChangedRequest;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class ExecutionChangedListener {

    private final CreateExecutionUseCase createExecutionUseCase;

    public ExecutionChangedListener(final CreateExecutionUseCase createExecutionUseCase) {
        this.createExecutionUseCase = createExecutionUseCase;
    }

    @SqsListener(value = "${aws.sqs.execution-event-changed-queue}")
    public void executionChangedListener(final @Payload ExecutionChangedRequest request) {
        if (ExecutionChangeReason.CREATED.equals(request.reason())) {
            resolveExecutionCreated(request);
            return;
        }

        if (ExecutionChangeReason.UPDATED.equals(request.reason())) {
            resolveExecutionUpdated(request);
            return;
        }

        if (ExecutionChangeReason.DELETED.equals(request.reason())) {
            resolveExecutionDeleted(request);
            return;
        }

    }

    private void resolveExecutionCreated(final ExecutionChangedRequest request) {
        ofNullable(request.execution())
                .ifPresent(execution -> {
                    createExecutionUseCase.execute(CreateExecutionCommandInput.with(
                            execution.id(),
                            execution.origin(),
                            execution.stockId(),
                            execution.walletId(),
                            execution.profitPercentage(),
                            execution.executedQuantity(),
                            execution.executedPrice(),
                            execution.executedVolume(),
                            execution.status()
                    ));
                });
    }

    private void resolveExecutionUpdated(final ExecutionChangedRequest request) {
        //todo: need to be implemented
    }

    private void resolveExecutionDeleted(final ExecutionChangedRequest request) {
        //todo: need to be implemented
    }

}
