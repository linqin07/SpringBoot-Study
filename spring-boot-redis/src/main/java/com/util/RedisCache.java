package com.util;

import com.config.JsonRedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisCache<K, V> {

    private JsonRedisTemplate jsonRedisTemplate;

    public RedisCache(Builder builder) {
        this.jsonRedisTemplate = builder.jsonRedisTemplate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean put(K key, V value) {
        jsonRedisTemplate.opsForValue().set(key, value);
        return true;
    }

    public boolean put(K key, V value, int ttl, TimeUnit timeUtil) {
        jsonRedisTemplate.opsForValue().set(key, value, ttl, timeUtil);
        return true;
    }
    public V getIfPresent(K key) {
        V v = (V) jsonRedisTemplate.opsForValue().get(key);
        return v;
    }

    public boolean invalidate(K key) {
        Boolean delete = jsonRedisTemplate.delete(key);
        return delete;
    }


    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private JsonRedisTemplate jsonRedisTemplate;


        public Builder setJsonRedisTemplate(JsonRedisTemplate jsonRedisTemplate) {
            this.jsonRedisTemplate = jsonRedisTemplate;
            return this;
        }

        public RedisCache build() {
            return new RedisCache(this);
        }
    }

}
