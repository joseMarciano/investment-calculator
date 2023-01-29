package com.investment.managment.repository.redis;

import com.investment.managment.execution.entity.ExecutionRedisEntity;
import org.springframework.data.repository.CrudRepository;

public interface ExecutionRedisRepository extends CrudRepository<ExecutionRedisEntity, String> {
}
