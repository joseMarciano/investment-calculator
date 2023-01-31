package com.investment.managment.execution;

import com.investment.managment.execution.entity.ExecutionRedisEntity;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.repository.redis.ExecutionRedisRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RedisExecutionGateway implements ExecutionGateway {

    private final ExecutionRedisRepository executionRedisRepository;

    public RedisExecutionGateway(final ExecutionRedisRepository executionRedisRepository) {
        this.executionRedisRepository = executionRedisRepository;
    }

    @Override
    public Execution create(final Execution anExecution) {
        return this.executionRedisRepository.save(ExecutionRedisEntity.from(anExecution)).toAggregate();
    }

    @Override
    public Optional<Execution> findById(final ExecutionID anId) {
        return this.executionRedisRepository.findById(anId.getValue())
                .map(ExecutionRedisEntity::toAggregate);
    }
}
