package com.investment.managment.user.entity;

import com.investment.managment.user.User;
import com.investment.managment.user.UserID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash("User")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserRedisEntity {

    @Id
    private String id;

    private boolean online;

    private LocalDateTime updatedAt;

    public static UserRedisEntity from(final User user) {
        return builder()
                .id(user.getId().getValue())
                .online(user.isOnline())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public User toAggregate() {
        return User.builder()
                .id(UserID.from(this.id))
                .online(this.online)
                .updatedAt(this.updatedAt)
                .build();
    }
}
