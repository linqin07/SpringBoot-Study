package com;

import com.config.JsonRedisTemplate;
import com.util.RedisCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringBootRedisApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JsonRedisTemplate jsonRedisTemplate;

    private RedisCache<String, List<User>> listRedisCache;
    private RedisCache<String, User> userRedisCache;
    private RedisCache<String, Map<String, Object>> mapRedisCache;



    @BeforeEach
    void init() {
        listRedisCache = RedisCache.builder().setJsonRedisTemplate(jsonRedisTemplate).build();
        userRedisCache = RedisCache.builder().setJsonRedisTemplate(jsonRedisTemplate).build();
        mapRedisCache = RedisCache.builder().setJsonRedisTemplate(jsonRedisTemplate).build();
    }

    @Test
    void redis() {
        stringRedisTemplate.opsForValue().set("key", "value");

        jsonRedisTemplate.opsForValue().set("user", new User(1L, "对象"));

        List list = new LinkedList();
        list.add(new User(1L, "1对象"));
        list.add(new User(2L, "2对象"));
        jsonRedisTemplate.opsForValue().set("list", list);
    }

    @Test
    void redisCache() {
        List list = new LinkedList();
        list.add(new User(1L, "1对象"));
        list.add(new User(2L, "2对象"));
        listRedisCache.put("1", list);
        List<User> ifPresent = listRedisCache.getIfPresent("1");
    }

    @Test
    void del() {
        listRedisCache.invalidate("1");
        listRedisCache.invalidate("key");
    }

}
