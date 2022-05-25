package com.dahuang.web.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

/**
 * 描述: Netty WebSocket服务器
 *      使用独立的线程启动
 * @author dahuang
 * @version 1.0
 * @date 2021.4.26
 */
public class WebSocketServer implements Runnable{

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

	//创建两个线程组bossGroup和workerGroup, 含有的子线程NioEventLoop的个数默认为cpu核数的两倍
    // 只处理客户端连接请求
    @Resource
	private EventLoopGroup bossGroup;

    // 客户端业务处理
    @Resource
	private EventLoopGroup workerGroup;

    // 辅助服务器启对象
    @Resource
	private ServerBootstrap serverBootstrap;

	@Value("9090")
	private int port;

	@Resource(name = "childChannelHandler")
	private ChannelHandler childChannelHandler;

	private ChannelFuture serverChannelFuture;


	public WebSocketServer() {

	}

	@Override
	public void run() {
        build();
	}

	/**
	 * 描述：启动Netty Websocket服务器
	 */
	public void build() {
		try {
		    long begin = System.currentTimeMillis();
			serverBootstrap.group(bossGroup, workerGroup) //boss辅助客户端的tcp连接请求  worker负责与客户端之间的读写操作
						   .channel(NioServerSocketChannel.class) //配置客户端的channel类型为 NioServerSocketChannel
						   .option(ChannelOption.SO_BACKLOG, 1024) //配置TCP参数，握手字符串长度设置
						   .option(ChannelOption.TCP_NODELAY, true) //TCP_NODELAY算法，尽可能发送大块数据，减少充斥的小块数据
						   .childOption(ChannelOption.SO_KEEPALIVE, true)//开启心跳包活机制，就是客户端、服务端建立连接处于ESTABLISHED状态，超过2小时没有交流，机制会被启动
						   .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))//配置固定长度接收缓存区分配器
						   .childHandler(childChannelHandler); //绑定I/O事件的处理类,WebSocketChildChannelHandler中定义
			long end = System.currentTimeMillis();
	        logger.info("Netty Websocket服务器启动完成，耗时 " + (end - begin) + " ms，已绑定端口 " + port + " 阻塞式等候客户端连接");

			/**
			 * 服务端对象绑定 port 端口并启动
			 *   sync():由于bind方法是异步操作，使用sync()方法是等待异步操作执行完毕。
			 */
	        serverChannelFuture = serverBootstrap.bind(port).sync();
		} catch (Exception e) {
		    logger.info(e.getMessage());
			bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            e.printStackTrace();
		}

	}

	/**
	 * 描述：关闭Netty Websocket服务器，主要是释放连接
	 *     连接包括：服务器连接serverChannel，
	 *     客户端TCP处理连接bossGroup，
	 *     客户端I/O操作连接workerGroup
	 *
	 *     若只使用
	 *         bossGroupFuture = bossGroup.shutdownGracefully();
	 *         workerGroupFuture = workerGroup.shutdownGracefully();
	 *     会造成内存泄漏。
	 */
	public void close(){
	    serverChannelFuture.channel().close();
		Future<?> bossGroupFuture = bossGroup.shutdownGracefully();
        Future<?> workerGroupFuture = workerGroup.shutdownGracefully();

        try {
            bossGroupFuture.await();
            workerGroupFuture.await();
        } catch (InterruptedException ignore) {
            ignore.printStackTrace();
        }
	}

}
