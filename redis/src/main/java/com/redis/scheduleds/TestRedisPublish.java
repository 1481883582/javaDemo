package com.redis.scheduleds;

import com.consts.RedisPublishConst;
import com.utils.LocalDateTimeUtil;
import com.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * 测试redis发布者模式
 */
@EnableScheduling
@Configuration
public class TestRedisPublish {

	@Autowired
	private RedisUtil redisUtil;

	@Scheduled(cron = "0/1 * * * * ? ") // 心跳 每间隔两秒执行一次
	public void task() {
		redisUtil.convertAndSend(RedisPublishConst.CHANNEL_MSG, LocalDateTimeUtil.format(new Date(), LocalDateTimeUtil.DATE_TIME_PATTERN));
	}
}
