package com.controller;

import com.config.JsonRedisTemplate;
import com.entity.LinkModel;
import com.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@RestController
public class RedisController {
    private static JsonRedisTemplate jsonRedisTemplate;
    static RedisCache<String, LinkModel> redisCache;
    static RedisCache<String, List<LinkModel>> redisCache1;
    static RedisCache<String, Map<String, Object>> mapRedisCache;

    @Autowired
    public void getJsonRedisTemplate(JsonRedisTemplate jsonRedisTemplate) {
        RedisController.jsonRedisTemplate = jsonRedisTemplate;
    }

    @PostConstruct
    public void init() {
        redisCache = RedisCache.newBuilder().setJsonRedisTemplate(jsonRedisTemplate).build();
        redisCache1 = RedisCache.newBuilder().setJsonRedisTemplate(jsonRedisTemplate).build();
        mapRedisCache = RedisCache.newBuilder().setJsonRedisTemplate(jsonRedisTemplate).build();

    }

    @RequestMapping("/testRedis")
    public String testRedis() {
        redisCache.put("key", new LinkModel());
        return "";
    }
}

