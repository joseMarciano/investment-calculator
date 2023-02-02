package com.investment.managment.api.execution.listener;

import com.investment.managment.execution.ExecutionChangeReason;
import com.investment.managment.execution.create.CreateExecutionCommandInput;
import com.investment.managment.execution.create.CreateExecutionUseCase;
import com.investment.managment.execution.deleteById.DeleteExecutionByIdCommandInput;
import com.investment.managment.execution.deleteById.DeleteExecutionByIdUseCase;
import com.investment.managment.execution.models.ExecutionChangedRequest;
import com.investment.managment.execution.update.UpdateExecutionCommandInput;
import com.investment.managment.execution.update.UpdateExecutionUseCase;
import com.investment.managment.stock.RedisStockGateway;
import com.investment.managment.stock.StockUsed;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

import static com.investment.managment.execution.models.ExecutionChangedRequest.ExecutionDTO;
import static java.util.Optional.ofNullable;

@Component
public class ExecutionChangedListener {

    private final CreateExecutionUseCase createExecutionUseCase;
    private final UpdateExecutionUseCase updateExecutionUseCase;
    private final DeleteExecutionByIdUseCase deleteExecutionByIdUseCase;
    private final RedisStockGateway redisStockGateway;

    public ExecutionChangedListener(final CreateExecutionUseCase createExecutionUseCase,
                                    final UpdateExecutionUseCase updateExecutionUseCase,
                                    final DeleteExecutionByIdUseCase deleteExecutionByIdUseCase,
                                    final RedisStockGateway redisStockGateway) {
        this.createExecutionUseCase = createExecutionUseCase;
        this.updateExecutionUseCase = updateExecutionUseCase;
        this.deleteExecutionByIdUseCase = deleteExecutionByIdUseCase;
        this.redisStockGateway = redisStockGateway;
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
        getExecution(request)
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

        this.redisStockGateway.findById(request.execution().stockId())
                .ifPresent((stock) -> redisStockGateway.addStockUsed(Set.of(StockUsed.builder().id(stock.getId()).symbol(stock.getSymbol()).build())));
    }


    private void resolveExecutionUpdated(final ExecutionChangedRequest request) {
        getExecution(request)
                .ifPresent(execution -> {
                    updateExecutionUseCase.execute(UpdateExecutionCommandInput.with(
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

    private void resolveExecutionDeleted(final ExecutionChangedRequest request) {
        getExecution(request)
                .ifPresent(execution -> {
                    this.deleteExecutionByIdUseCase.execute(DeleteExecutionByIdCommandInput.with(execution.id()));
                });
    }


    private static Optional<ExecutionDTO> getExecution(final ExecutionChangedRequest request) {
        return ofNullable(request.execution());
    }

}
