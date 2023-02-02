package com.investment.managment.execution;

import com.investment.managment.execution.entity.ExecutionRedisEntity;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.repository.redis.ExecutionRedisRepository;
import com.investment.managment.validation.exception.DomainExeceptionFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class RedisExecutionGateway implements ExecutionGateway {

    private final ExecutionRedisRepository executionRedisRepository;

    public RedisExecutionGateway(final ExecutionRedisRepository executionRedisRepository) {
        this.executionRedisRepository = executionRedisRepository;
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

    private Execution save(final Execution anExecution) {
        return this.executionRedisRepository.save(ExecutionRedisEntity.from(anExecution)).toAggregate();
    }
}
