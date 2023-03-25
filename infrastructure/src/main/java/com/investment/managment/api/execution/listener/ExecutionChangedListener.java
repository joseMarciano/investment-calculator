package com.investment.managment.api.execution.listener;

import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionChangeReason;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.execution.RedisExecutionGateway;
import com.investment.managment.execution.create.CreateExecutionCommandInput;
import com.investment.managment.execution.create.CreateExecutionUseCase;
import com.investment.managment.execution.deleteById.DeleteExecutionByIdCommandInput;
import com.investment.managment.execution.deleteById.DeleteExecutionByIdUseCase;
import com.investment.managment.execution.entity.ExecutionRedisEntity;
import com.investment.managment.execution.models.ExecutionChangedRequest;
import com.investment.managment.execution.update.UpdateExecutionCommandInput;
import com.investment.managment.execution.update.UpdateExecutionUseCase;
import com.investment.managment.repository.redis.ExecutionRedisRepository;
import com.investment.managment.stock.RedisStockGateway;
import com.investment.managment.stock.StockUsed;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import static com.investment.managment.execution.models.ExecutionChangedRequest.ExecutionDTO;
import static java.util.Optional.ofNullable;

@Component
public class ExecutionChangedListener {

    private final CreateExecutionUseCase createExecutionUseCase;
    private final UpdateExecutionUseCase updateExecutionUseCase;
    private final DeleteExecutionByIdUseCase deleteExecutionByIdUseCase;
    private final RedisStockGateway redisStockGateway;
    private final RedisExecutionGateway redisExecutionGateway;
    private final ExecutionRedisRepository executionRedisRepository;

    public ExecutionChangedListener(final CreateExecutionUseCase createExecutionUseCase,
                                    final UpdateExecutionUseCase updateExecutionUseCase,
                                    final DeleteExecutionByIdUseCase deleteExecutionByIdUseCase,
                                    final RedisStockGateway redisStockGateway,
                                    final RedisExecutionGateway redisExecutionGateway,
                                    final ExecutionRedisRepository executionRedisRepository) {
        this.createExecutionUseCase = createExecutionUseCase;
        this.updateExecutionUseCase = updateExecutionUseCase;
        this.deleteExecutionByIdUseCase = deleteExecutionByIdUseCase;
        this.redisStockGateway = redisStockGateway;
        this.redisExecutionGateway = redisExecutionGateway;
        this.executionRedisRepository = executionRedisRepository;
    }

    @SqsListener(value = "${aws.sqs.execution-event-changed-queue}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
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
        final var executionDtoOpt = getExecution(request);

        if (executionDtoOpt.isEmpty()) return;
        var executionDTO = executionDtoOpt.get();

        createExecutionUseCase.execute(CreateExecutionCommandInput.with(
                executionDTO.id(),
                executionDTO.origin(),
                executionDTO.stockId(),
                executionDTO.walletId(),
                executionDTO.profitPercentage(),
                executionDTO.executedQuantity(),
                executionDTO.executedPrice(),
                executionDTO.executedVolume(),
                executionDTO.status()
        ));

        handlerExecutionsSold(executionDTO, execution -> execution.addExecutionSold(executionDTO.id()));

        this.redisStockGateway.findById(request.execution().stockId())
                .ifPresent((stock) -> redisStockGateway.addStockUsed(Set.of(StockUsed.builder().id(stock.getId()).symbol(stock.getSymbol()).build())));
    }


    private void resolveExecutionUpdated(final ExecutionChangedRequest request) {
        final var executionDtoOpt = getExecution(request);

        if (executionDtoOpt.isEmpty()) return;
        var executionDTO = executionDtoOpt.get();

        this.executionRedisRepository.findById(executionDTO.id().getValue())
                .map(ExecutionRedisEntity::toAggregate)
                .ifPresent(execution -> updateExecutionUseCase.execute(UpdateExecutionCommandInput.with(
                        executionDTO.id(),
                        executionDTO.origin(),
                        executionDTO.stockId(),
                        executionDTO.walletId(),
                        executionDTO.profitPercentage(),
                        executionDTO.executedQuantity(),
                        executionDTO.executedPrice(),
                        executionDTO.executedVolume(),
                        executionDTO.status(),
                        execution.getExecutionsSold()
                )));
    }

    private void resolveExecutionDeleted(final ExecutionChangedRequest request) {
        final var executionDtoOpt = getExecution(request);

        if (executionDtoOpt.isEmpty()) return;
        var executionDTO = executionDtoOpt.get();

        this.executionRedisRepository.findById(executionDTO.id().getValue())
                .map(ExecutionRedisEntity::toAggregate)
                .ifPresent(it -> {
                    this.deleteExecutionByIdUseCase.execute(DeleteExecutionByIdCommandInput.with(executionDTO.id()));
                    handlerExecutionsSold(ExecutionDTO.with(it.getId(), it.getOrigin(), it.getStatus()), execution -> execution.removeExecutionSold(executionDTO.id()));
                });
    }

    private static Optional<ExecutionDTO> getExecution(final ExecutionChangedRequest request) {
        return ofNullable(request.execution());
    }

    private void handlerExecutionsSold(final ExecutionDTO executionDTO, final Consumer<Execution> callback) {
        if (!ExecutionStatus.SELL.equals(executionDTO.status())) return;

        final var executionOpt = executionRedisRepository.findById(executionDTO.origin().getValue());
        if (executionOpt.isEmpty()) return;

        final var execution = executionOpt.get().toAggregate();
        callback.accept(execution);

        updateExecutionUseCase.execute(UpdateExecutionCommandInput.with(
                execution.getId(),
                execution.getOrigin(),
                execution.getStockId(),
                execution.getWalletId(),
                execution.getProfitPercentage(),
                execution.getExecutedQuantity(),
                execution.getExecutedPrice(),
                execution.getExecutedVolume(),
                execution.getStatus(),
                execution.getExecutionsSold()
        ));
    }
}
