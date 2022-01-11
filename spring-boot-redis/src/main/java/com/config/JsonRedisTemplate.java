package com.config;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class JsonRedisTemplate<T> extends RedisTemplate<String, T> {
    public JsonRedisTemplate() {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        setKeySerializer(stringRedisSerializer);
        setValueSerializer(jackson2JsonRedisSerializer);
        setHashKeySerializer(stringRedisSerializer);
        setHashValueSerializer(jackson2JsonRedisSerializer);
    }

    public JsonRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

//    @Override
//    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
//        return super.preProcessConnection(connection, existingConnection);
//    }
}
