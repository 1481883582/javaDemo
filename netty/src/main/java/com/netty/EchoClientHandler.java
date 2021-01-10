package com.netty;

import java.nio.charset.Charset;
import java.util.Scanner;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	public static volatile ChannelHandlerContext channelHandlerContext;
	/**
	 * 向服务端发送数据
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		channelHandlerContext = ctx;
		System.out.println("客户端与服务端通道-开启：" + ctx.channel().localAddress() + "channelActive");
		EchoClientHandler.channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("客户端连接成功", CharsetUtil.UTF_8));
	}

	/**
	 * channelInactive
	 *
	 * channel 通道 Inactive 不活跃的
	 *
	 * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
	 *
	 */
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端与服务端通道-关闭：" + ctx.channel().localAddress() + "channelInactive");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		System.out.println("读取客户端通道信息..");
		ByteBuf buf = msg.readBytes(msg.readableBytes());
//		System.out.println(
//				"客户端接收到的服务端信息:" + ByteBufUtil.hexDump(buf) + "; 数据包为:" + buf.toString(Charset.forName("utf-8")));
		log.info("客户端接收到的服务端信息:" + buf.toString(CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
		System.out.println("异常退出:" + cause.getMessage());
	}
}
