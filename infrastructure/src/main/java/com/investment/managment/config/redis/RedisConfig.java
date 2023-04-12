package com.investment.managment.config.redis;

import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(
        basePackages = {
                "com.investment.managment.repository.redis"
        }
)
public class RedisConfig {

    @Bean
    LettuceConnectionFactory lettuceConnectionFactory(@Value("${redis.host}") final String host) {
        final var redisUri = RedisURI.create(host);
        redisUri.setVerifyPeer(false);

        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(redisUri.getHost(), redisUri.getPort());
        redisConfig.setPassword(redisUri.getPassword());

        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(final LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
