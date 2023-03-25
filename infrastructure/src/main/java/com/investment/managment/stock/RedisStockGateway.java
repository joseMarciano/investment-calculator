package com.investment.managment.stock;

import com.investment.managment.repository.redis.StockRedisRepository;
import com.investment.managment.repository.redis.StockUsedRedisRepository;
import com.investment.managment.stock.entity.StockRedisEntity;
import com.investment.managment.stock.entity.StockUsedRedisEntity;
import com.investment.managment.stock.gateway.StockGateway;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class RedisStockGateway implements StockGateway {

    private static final String STOCKS_USED_KEY = "used-stocks";
    private final StockRedisRepository stockRedisRepository;
    private final StockUsedRedisRepository stockUsedRedisRepository;

    public RedisStockGateway(final StockRedisRepository stockRedisRepository,
                             final StockUsedRedisRepository stockUsedRedisRepository) {
        this.stockRedisRepository = stockRedisRepository;
        this.stockUsedRedisRepository = stockUsedRedisRepository;
    }

    @Override
    public Stock create(final Stock aStock) {
        return save(aStock);
    }

    @Override
    public Optional<Stock> findById(final StockID anId) {
        return this.stockRedisRepository.findById(anId.getValue())
                .map(StockRedisEntity::toAggregate);
    }

    @Override
    public Set<StockUsed> findUsedStocks() {
        final var spliterator = this.stockUsedRedisRepository.findAll().spliterator();
        return StreamSupport.stream(spliterator, false)
                .filter(Objects::nonNull)
                .map(StockUsedRedisEntity::toAggregate)
                .collect(Collectors.toSet());
    }

    @Override
    public void addStockUsed(final Set<StockUsed> symbols) {
        this.stockUsedRedisRepository.saveAll(symbols.stream().map(StockUsedRedisEntity::from).collect(Collectors.toSet()));
    }

    @Override
    public void clearAllStockUsed() {
        this.stockUsedRedisRepository.deleteAll();
    }

    @Override
    public Stock update(final Stock aStock) {
        return this.save(aStock);
    }

    private Stock save(final Stock aStock) {
        return this.stockRedisRepository.save(StockRedisEntity.from(aStock)).toAggregate();
    }
}
