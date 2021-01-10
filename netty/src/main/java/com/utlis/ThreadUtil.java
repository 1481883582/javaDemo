package com.utlis;

import com.interfaces.NettySend;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class ThreadUtil {

	/**
	 * 定义线程名字
	 * @param name
	 * @return
	 */
	public static synchronized ExecutorService newSingleThreadExecutor(String name){
		ThreadFactory threadFactory = new CustomizableThreadFactory(name);
		ExecutorService singleThreadExecutor = new ThreadPoolExecutor(1, 1,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(),
				threadFactory);
		return singleThreadExecutor;
	}

	/**
	 *  线程运行
	 * @param name   线程名
	 * @param runnable   运行方法
	 */
	public static synchronized void ThreadRun(String name, Runnable runnable){
		ThreadUtil.newSingleThreadExecutor("Thread-" + name).execute(() -> {
			runnable.run();
		});
		log.info("Thread-{}线程结束", name);
	}

	/**
	 * 储存netty 客户端
	 */
	public static volatile Map<String, ChannelHandlerContext> nettyMap = new ConcurrentHashMap<>();

	/**
	 * 给全部客户  发送消息
	 *
	 * @param msg
	 */
	public static synchronized void sendALL(String msg) {

		sendExample((k, v) -> {

			send(v, msg);
		});
	}


	/**
	 * 给指定客户端id 发送消息
	 * @param id
	 * @param msg
	 */
	public static synchronized void send(String id, String msg) {

		sendExample((k, v) -> {

			if (k.equalsIgnoreCase(id)) {
				send(v, msg);
			}
		});

	}

	/**
	 * 发送消息
	 * @param handlerContext 指定的发送通道
	 * @param msg   发送的详细数据
	 */
	public static synchronized void send(ChannelHandlerContext handlerContext, String msg) {

		if (handlerContext.channel().isOpen()) {
			handlerContext.channel().writeAndFlush(msg.getBytes(CharsetUtil.UTF_8));
			log.info("给id-{}发送了消息-{}", handlerContext, msg);
		}

	}

	/**
	 * Netty发送客户端
	 * @param nettySend 发送运行的内容
	 */
	public static synchronized void sendExample(NettySend nettySend) {
		//为null直接返回
		if (nettyMap.isEmpty()) return;

		nettyMap.forEach((k, v) -> {
			//判断连接后做实际操作
			nettySend.run(k, v);
		});
	}

}

