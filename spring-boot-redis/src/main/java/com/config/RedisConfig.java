package com.config;

import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisOperations;

import java.time.Duration;

@Configuration
@ConditionalOnClass({RedisOperations.class})
@EnableConfigurationProperties({RedisProperties.class})
public class RedisConfig {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 集群配置
     *
     * @param redisProperties
     * @return
     */
    @Bean
    @ConditionalOnProperty(
            prefix = "spring.redis.cluster",
            name = {"nodes"}
    )
    public LettuceConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        logger.info("初始化redis集群: {}", redisProperties.getHost());
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
        if (redisProperties.getPassword() != null) {
            redisClusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        }
        // https://github.com/lettuce-io/lettuce-core/wiki/Redis-Cluster#user-content-refreshing-the-cluster-topology-view
        ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                .enablePeriodicRefresh()
                .enableAllAdaptiveRefreshTriggers()
                .refreshPeriod(Duration.ofMinutes(2))
                .build();

        ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
                .autoReconnect(true)
                .pingBeforeActivateConnection(true)
                .topologyRefreshOptions(clusterTopologyRefreshOptions).build();

        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder
                builder = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(5))
                //.readFrom(ReadFrom.REPLICA_PREFERRED) //设置了会导致分布式锁操作从节点报错
                .clientOptions(clusterClientOptions);

        //连接池配置
//        RedisProperties.Pool pool = redisProperties.getLettuce().getPool();
//        if (pool != null) {
//            GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
//            genericObjectPoolConfig.setMaxIdle(pool.getMaxIdle());
//            genericObjectPoolConfig.setMinIdle(pool.getMinIdle());
//            genericObjectPoolConfig.setMaxTotal(pool.getMaxActive());
//            genericObjectPoolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());
//            builder.poolConfig(genericObjectPoolConfig);
//        }

        // https://github.com/lettuce-io/lettuce-core/wiki/ReadFrom-Settings
        LettuceClientConfiguration lettuceClientConfiguration = builder.build();
        return new LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration);
    }

    //    @Bean(name = "jsonRedisTemplate")
//    @ConditionalOnMissingBean(name = {"jsonRedisTemplate"})
//    public RedisTemplate<String, Object> jsonRedisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//        template.setConnectionFactory(factory);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        // key采用String的序列化方式
//        template.setKeySerializer(stringRedisSerializer);
//        // hash的key也采用String的序列化方式
//        template.setHashKeySerializer(stringRedisSerializer);
//        // value序列化方式采用jackson
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        // hash的value序列化方式采用jackson
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//        return template;
//    }
    @Bean(name = "jsonRedisTemplate")
    public <T> JsonRedisTemplate<T> jsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        JsonRedisTemplate<T> redisTemplate = new JsonRedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }


}

