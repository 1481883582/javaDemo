package com.config;

import com.enums.Jackson2Json;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * 并发用的  多个Redis
 */
@Configuration
public class LettuceConfig {

	/**
	 * 自定义LettuceConnectionFactory,这一步的作用就是返回根据你传入参数而配置的
	 * LettuceConnectionFactory，
	 * 也可以说是LettuceConnectionFactory的原理了，
	 * 后面我会详细讲解的,各位同学也可先自己看看源码
	 * <p>
	 * 这里定义的方法 createLettuceConnectionFactory，方便快速使用
	 */
	private synchronized static LettuceConnectionFactory createLettuceConnectionFactory(
			int dbIndex, String hostName, int port, String password,
			int maxIdle, int minIdle, int maxActive,
			Long maxWait, Long timeOut, Long shutdownTimeOut) {

		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(hostName);
		redisStandaloneConfiguration.setPort(port);
		redisStandaloneConfiguration.setDatabase(dbIndex);
		redisStandaloneConfiguration.setPassword(password);

		//连接池配置
		GenericObjectPoolConfig genericObjectPoolConfig =
				new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxIdle(maxIdle);
		genericObjectPoolConfig.setMinIdle(minIdle);
		genericObjectPoolConfig.setMaxTotal(maxActive);
		genericObjectPoolConfig.setMaxWaitMillis(maxWait);


		//redis客户端配置
		LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder
				builder = LettucePoolingClientConfiguration.builder().
				commandTimeout(Duration.ofMillis(timeOut));

		builder.shutdownTimeout(Duration.ofMillis(shutdownTimeOut));
		builder.poolConfig(genericObjectPoolConfig);
		LettuceClientConfiguration lettuceClientConfiguration = builder.build();

		//根据配置和客户端配置创建连接
		LettuceConnectionFactory lettuceConnectionFactory = new
				LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
		lettuceConnectionFactory.afterPropertiesSet();

		return lettuceConnectionFactory;
	}

	/**
	 * 配置 cache RedisTemplate
	 *
	 * @return RedisTemplate<String, Serializable>r
	 */
	public synchronized static StringRedisTemplate getCacheRedisTemplate(int dbIndex, String hostName, int port, String password,
	                                                                     int maxIdle, int minIdle, int maxActive,
	                                                                     Long maxWait, Long timeOut, Long shutdownTimeOut) {
		//创建客户端连接
		LettuceConnectionFactory lettuceConnectionFactory =
				createLettuceConnectionFactory
						(dbIndex, hostName, port, password, maxIdle, minIdle, maxActive, maxWait, timeOut, shutdownTimeOut);
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);
		/**
		 * 使用 String 作为 Key 的序列化器,使用 Jackson 作为 Value 的序列化器
		 */
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// key采用String的序列化方式
		redisTemplate.setKeySerializer(stringRedisSerializer);
		// hash的key也采用String的序列化方式
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		// value序列化方式采用jackson
		redisTemplate.setValueSerializer(Jackson2Json.INSTANCE.getInstance());
		// hash的value序列化方式采用jackson
		redisTemplate.setHashValueSerializer(Jackson2Json.INSTANCE.getInstance());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	public synchronized static StringRedisTemplate getCacheRedisTemplate(String hostName, String password) {
		//创建客户端连接
		return getCacheRedisTemplate(0, hostName, 6379, password, 10, 0, 20, -1L, 100L, 4000L);
	}
}
