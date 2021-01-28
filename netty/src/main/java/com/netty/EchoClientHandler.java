package com.netty;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

import com.alibaba.druid.sql.visitor.functions.Char;
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
		EchoClientHandler.channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("客户端连接成功", StandardCharsets.US_ASCII));
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
		try {
			ByteBuf buf = msg;
//创建目标大小的数组
			byte[] barray = new byte[buf.readableBytes()];
//把数据从bytebuf转移到byte[]
			buf.getBytes(0,barray);
//			log.info(Arrays.toString(barray));
//			System.out.println();
			for (int i=0; i<barray.length; i++)
			{

				if(i<10){
					if(i<2){
						System.out.print(Integer.toHexString(barray[i] & 0xff));
						continue;
					}
					System.out.print(barray[i] & 0xff);
					continue;
				}
				if (barray[i] < 0)
					System.out.print((char)(barray[i] & 0xff));
				else
					System.out.print((char)(barray[i]));
			}
			System.out.println();
			//将byte[]转成字符串用于打印
//			String str=new String(barray);
//			if (str.length()>0)
//			{
//				System.out.println(str);
//				System.out.flush();
//			}
//			else
//			{
//				System.out.println("不能读啊");
//			}
//			buf.release();
		}finally {
//buf.release();
		}
//		System.out.println("读取客户端通道信息..");
//		ByteBuf buf = msg.readBytes(msg.readableBytes());
////		System.out.println(
////				"客户端接收到的服务端信息:" + ByteBufUtil.hexDump(buf) + "; 数据包为:" + buf.toString(Charset.forName("utf-8")));
//		log.info("客户端接收到的服务端信息:" + buf.toString(CharsetUtil.UTF_8));
	}



	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
		System.out.println("异常退出:" + cause.getMessage());
	}
}
