package com.investment.managment.repository.redis;

import com.investment.managment.execution.entity.summary.ExecutionSummaryRedisEntity;
import org.springframework.data.repository.CrudRepository;

public interface ExecutionSummaryRedisRepository extends CrudRepository<ExecutionSummaryRedisEntity, String> {
}
