package com.tech.skill.forge.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

/**
 * @author yanmiao.wu
 * @create 2023-11-20 16:19
 */
@Configuration
//@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {

    @Autowired
    RedisProperties redisProperties;

    /**
     * 生成通用redisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    /**
     * <p>使用lettuce连接池</p>
     * <a href= "https://support.huaweicloud.com/usermanual-dcs/dcs-ug-211105002.html">Lettuce池化代码示例</a>
     * <p>因lettuce底层采用基于netty的NIO模式，和redis server进行通信，不同于jedis的BIO模式。底层采用长连接 + 队列的组合模式，借助TCP顺序发、顺序收的特性，来实现同时处理多请求发送和多响应接收，单条连接可支撑的QPS在3K~5K不等，线上系统建议不要超过3K。lettuce本身不支持池化，且在springboot中默认不开启池化，如需开启池化，需通过手动引入commons-pool2组件，并关闭LettuceConnectionFactory.shareNativeConnection（共享连接）来实现池化。</p>
     * <p>因每条lettuce连接默认需要配置两个线程池-I/O thread pools、computation thread pool，用于支撑IO事件读取和异步event处理，如配置成连接池形式使用，每个连接都将会创建两个线程池，对内存资源的占用偏高。鉴于lettuce的底层模型实现，及单连接突出的处理能力，不建议通过池化的方式使用lettuce。</p>
     * <p>因lettuce底层采用的是单长连接 + 请求队列的组合模式，一旦遇到网络抖动/闪断，或连接失活，将影响所有请求，尤其是在连接失活场景中，将尝试tcp重传，直至重传超时关闭连接，待连接重建后才能恢复。在重传期间请求队列会不断堆积请求，上层业务非常容易出现批量超时，甚至在部分操作系统内核中的重传超时配置过长，致使业务系统长时间处于不可用状态。因此，不推荐使用lettuce组件，建议用jedis组件替换。</p>
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(LettuceClientConfiguration clientConfiguration) {

        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(redisProperties.getHost());
        standaloneConfiguration.setPort(redisProperties.getPort());
        standaloneConfiguration.setDatabase(redisProperties.getDatabase());
        standaloneConfiguration.setPassword(redisProperties.getPassword());

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(standaloneConfiguration, clientConfiguration);
        connectionFactory.setDatabase(redisProperties.getDatabase());
        //关闭共享链接，才能池化生效
        connectionFactory.setShareNativeConnection(false);
        return connectionFactory;
    }

    @Bean
    public LettuceClientConfiguration clientConfiguration() {

        SocketOptions socketOptions = SocketOptions.builder().connectTimeout(redisProperties.getConnectTimeout()).build();

        ClientOptions clientOptions = ClientOptions.builder()
                .autoReconnect(true)
                .pingBeforeActivateConnection(true)
                .cancelCommandsOnReconnectFailure(false)
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.ACCEPT_COMMANDS)
                .socketOptions(socketOptions)
                .build();

        LettucePoolingClientConfiguration poolingClientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(redisPoolConfig())
                .commandTimeout(redisProperties.getConnectTimeout())
                .clientOptions(clientOptions)
                .build();
        return poolingClientConfiguration;
    }

    private GenericObjectPoolConfig redisPoolConfig() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        //连接池的最小连接数
        poolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
        //连接池的最大空闲连接数
        poolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        //连接池的最大连接数
        poolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
        //连接池耗尽后是否需要等待，默认true表示等待。当值为true时，setMaxWait才会生效
        poolConfig.setBlockWhenExhausted(true);
        //连接池耗尽后获取连接的最大等待时间，默认-1表示一直等待
        poolConfig.setMaxWait(redisProperties.getLettuce().getPool().getMaxWait());
        //创建连接时校验有效性(ping)，默认false
        poolConfig.setTestOnCreate(false);
        //获取连接时校验有效性(ping)，默认false，业务量大时建议设置为false减少开销
        poolConfig.setTestOnBorrow(true);
        //归还连接时校验有效性(ping)，默认false，业务量大时建议设置为false减少开销
        poolConfig.setTestOnReturn(false);
        //是否开启空闲连接检测，如为false，则不剔除空闲连接
        poolConfig.setTestWhileIdle(true);
        //连接空闲多久后逐出，当空闲时间>该值，并且空闲连接>最大空闲数时直接逐出
        poolConfig.setSoftMinEvictableIdleTime(Duration.ofMillis(10000));
        //关闭根据MinEvictableIdleTimeMillis判断逐出
        poolConfig.setMinEvictableIdleTime(Duration.ofMillis(-1));
        //空闲连接逐出的检测周期，默认为60s
        poolConfig.setTimeBetweenEvictionRuns(Duration.ofMillis(60000));
        return poolConfig;
    }
}
