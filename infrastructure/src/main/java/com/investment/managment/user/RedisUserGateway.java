package com.investment.managment.user;

import com.investment.managment.repository.redis.UserRedisRepository;
import com.investment.managment.user.entity.UserRedisEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class RedisUserGateway implements UserGateway {

    private final UserRedisRepository userRedisRepository;

    public RedisUserGateway(final UserRedisRepository userRedisRepository) {
        this.userRedisRepository = userRedisRepository;
    }

    @Override
    public User update(final User aUser) {
        return this.save(aUser);
    }


    @Override
    public Set<User> findAll() {
        final var spliterator = this.userRedisRepository.findAll().spliterator();
        return StreamSupport.stream(spliterator, false)
                .filter(Objects::nonNull)
                .map(UserRedisEntity::toAggregate)
                .collect(Collectors.toSet());
    }

    @Override
    public void removeById(final UserID userID) {
        this.userRedisRepository.deleteById(userID.getValue());
    }

    private User save(final User aUser) {
        return this.userRedisRepository.save(UserRedisEntity.from(aUser)).toAggregate();
    }

}
