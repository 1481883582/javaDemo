package com.netty;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.utlis.ThreadUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoServer {
	private final int port;

	public EchoServer(int port) {
		this.port = port;
	}

	public void start() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();

		EventLoopGroup group = new NioEventLoopGroup();
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.option(ChannelOption.SO_BACKLOG, 1024);
			sb.group(group, bossGroup) // 绑定线程池
					.channel(NioServerSocketChannel.class) // 指定使用的channel
					.localAddress(this.port)// 绑定监听端口
					.childHandler(new ChannelInitializer<SocketChannel>() { // 绑定客户端连接时候触发操作

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							System.out.println("报告");
							System.out.println("信息：有一客户端链接到本服务端");
							System.out.println("IP:" + ch.localAddress().getHostName());
							System.out.println("Port:" + ch.localAddress().getPort());
							System.out.println("报告完毕");

							ch.pipeline().addLast(new StringEncoder(StandardCharsets.US_ASCII));
							ch.pipeline().addLast(new EchoServerHandler()); // 客户端触发操作
							ch.pipeline().addLast(new ByteArrayEncoder());
						}
					});
			ChannelFuture cf = sb.bind().sync(); // 服务器异步创建绑定
			System.out.println(EchoServer.class + " 启动正在监听： " + cf.channel().localAddress());

			//测试

			new Thread(() -> {
				while (true) {
					Scanner scanner = new Scanner(System.in);
					log.info("请输入服务端需要发送的数据:");
					String next = scanner.next();
					ThreadUtil.sendALL(next);
				}
			}).start();


			cf.channel().closeFuture().sync(); // 关闭服务器通道
		} finally {
			group.shutdownGracefully().sync(); // 释放线程池资源
			bossGroup.shutdownGracefully().sync();
		}
	}

	public static void main(String[] args) throws Exception {

		new EchoServer(55006).start(); // 启动


	}
}