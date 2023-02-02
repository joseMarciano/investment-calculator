package com.investment.managment.repository.redis;

import com.investment.managment.stock.entity.StockRedisEntity;
import org.springframework.data.repository.CrudRepository;

public interface StockRedisRepository extends CrudRepository<StockRedisEntity, String> {
}
