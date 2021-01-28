package com.netty;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoClient {
	private final String host;
	private final int port;

	public EchoClient() {
		this(0);
	}

	public EchoClient(int port) {
		this("localhost", port);
	}

	public EchoClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group) // 注册线程池
					.channel(NioSocketChannel.class) // 使用NioSocketChannel来作为连接用的channel类
					.remoteAddress(new InetSocketAddress(this.host, this.port)) // 绑定连接端口和host信息
					.handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							System.out.println("正在连接中...");
							ch.pipeline().addLast(new StringEncoder(StandardCharsets.US_ASCII));
							ch.pipeline().addLast(new EchoClientHandler());
							ch.pipeline().addLast(new ByteArrayEncoder());
							ch.pipeline().addLast(new ChunkedWriteHandler());

						}
					});
			// System.out.println("服务端连接成功..");

			ChannelFuture cf = b.connect().sync(); // 异步连接服务器
			System.out.println("服务端连接成功..."); // 连接完成

//			cf.channel().closeFuture().sync(); // 异步等待关闭连接channel
//			System.out.println("连接已关闭.."); // 关闭完成

		} finally {
//			group.shutdownGracefully().sync(); // 释放线程池资源
		}
	}

	public static void main(String[] args) throws Exception {
		EchoClient client = new EchoClient("127.0.0.1", 7788); // 连接127.0.0.1/65535，并启动
		client.start();


		new Thread(() -> {
			while (true) {
				Scanner scanner = new Scanner(System.in);
				log.info("请输入客户端需要发送的数据:");
				String next = scanner.next();
				EchoClientHandler.channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer(next,StandardCharsets.US_ASCII)); // 必须有flush
			}
		}).start();


	}
}
