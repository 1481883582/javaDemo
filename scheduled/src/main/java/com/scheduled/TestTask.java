package com.scheduled;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
@EnableScheduling
@Configuration
public class TestTask {

	@Scheduled(cron = "0/3 * * * * ? ") // 心跳 每间隔两秒执行一次
	public void task() {
		System.out.println("11111111111111111111111111111");
	}
}
