package com.swagger.listeners;

import com.listeners.RedisListener;
import com.utils.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestListener implements RedisListener {
	@Override
	public void onMessage(Message message, byte[] pattern) {
		log.info("TestListener订阅通道-{}  消息-{}",
				Strings.toString(message.getChannel()),
				Strings.toString(message.getBody())
		);

		String msg = Strings.toString(message.getBody());
		log.info("TestListener收到："+ msg);
	}
}
