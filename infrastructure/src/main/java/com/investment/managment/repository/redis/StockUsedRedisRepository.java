package com.investment.managment.repository.redis;

import com.investment.managment.stock.entity.StockRedisEntity;
import com.investment.managment.stock.entity.StockUsedRedisEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StockUsedRedisRepository extends CrudRepository<StockUsedRedisEntity, String> {
}
