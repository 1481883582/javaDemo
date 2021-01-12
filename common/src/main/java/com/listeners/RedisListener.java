package com.listeners;

import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

/**
 * redis 监听 数据
 */
@Service
public interface RedisListener extends MessageListener {
}
