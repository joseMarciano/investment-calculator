package com.investment.managment.execution;

import com.investment.managment.execution.entity.ExecutionRedisEntity;
import com.investment.managment.execution.entity.summary.ExecutionSummaryRedisEntity;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.execution.sumary.ExecutionSummary;
import com.investment.managment.execution.sumary.ExecutionSummaryID;
import com.investment.managment.repository.redis.ExecutionRedisRepository;
import com.investment.managment.repository.redis.ExecutionSummaryRedisRepository;
import com.investment.managment.validation.exception.DomainExeceptionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class RedisExecutionGateway implements ExecutionGateway {

    private final ExecutionRedisRepository executionRedisRepository;
    private final ExecutionSummaryRedisRepository executionSummaryRedisRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisExecutionGateway(final ExecutionRedisRepository executionRedisRepository,
                                 final ExecutionSummaryRedisRepository executionSummaryRedisRepository,
                                 final RedisTemplate<String, Object> redisTemplate) {
        this.executionRedisRepository = executionRedisRepository;
        this.executionSummaryRedisRepository = executionSummaryRedisRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Execution create(final Execution anExecution) {
        return save(anExecution);
    }

    @Override
    public Optional<Execution> findById(final ExecutionID anId) {
        return this.executionRedisRepository.findById(anId.getValue())
                .map(ExecutionRedisEntity::toAggregate);
    }

    @Override
    public void deleteById(final ExecutionID id) {
        this.findById(id)
                .ifPresent(this::deleteById);
    }

    private void deleteById(final Execution execution) {
        this.executionRedisRepository.deleteById(execution.getId().getValue());
    }

    @Override
    public List<Execution> findAll() {
        final var spliterator = this.executionRedisRepository.findAll().spliterator();
        return StreamSupport.stream(spliterator, false)
                .filter(Objects::nonNull)
                .map(ExecutionRedisEntity::toAggregate)
                .collect(Collectors.toList());
    }

    @Override
    public Execution update(final Execution anExecution) {
        final var anId = anExecution.getId();
        return this.findById(anId)
                .map(e -> this.save(anExecution))
                .orElseThrow(() -> DomainExeceptionFactory.notFoundException(anId, Execution.class));
    }

    @Override
    public ExecutionSummary getOrCreateExecutionSummary(final ExecutionSummaryID anId) {
        final Supplier<ExecutionSummaryRedisEntity> buildExecutionSummary = () ->
                ExecutionSummaryRedisEntity.from(ExecutionSummary.builder()
                        .id(anId)
                        .build());

        return this.executionSummaryRedisRepository.findById(anId.getValue())
                .orElseGet(() -> ExecutionSummaryRedisEntity.from(updateExecutionSummary(buildExecutionSummary.get().toAggregate())))
                .toAggregate();
    }

    @Override
    public ExecutionSummary updateExecutionSummary(final ExecutionSummary executionSummary) {
        return this.executionSummaryRedisRepository.save(ExecutionSummaryRedisEntity.from(executionSummary))
                .toAggregate();
    }

    @Override
    public List<ExecutionSummary> findAllExecutionSummary() {
        final var spliterator = this.executionSummaryRedisRepository.findAll().spliterator();
        return StreamSupport.stream(spliterator, false)
                .filter(Objects::nonNull)
                .map(ExecutionSummaryRedisEntity::toAggregate)
                .collect(Collectors.toList());
    }

    private Execution save(final Execution anExecution) {
        return this.executionRedisRepository.save(ExecutionRedisEntity.from(anExecution)).toAggregate();
    }
}
