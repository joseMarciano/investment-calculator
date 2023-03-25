package com.investment.managment.repository.redis;

import com.investment.managment.user.entity.UserRedisEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRedisRepository extends CrudRepository<UserRedisEntity, String> {
}
