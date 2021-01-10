package com.interfaces;

import io.netty.channel.ChannelHandlerContext;

@FunctionalInterface
public interface NettySend {
	void run(String key, ChannelHandlerContext value);
}
