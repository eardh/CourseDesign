package com.dahuang.utils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述: 全局常量
 *      1. USER_TOKEN 用户认证的键，用来匹配http session中的对应userId；
 *      2. webSocketServerHandshaker表，用channelId为键，存放握手实例。用来响应CloseWebSocketFrame的请求；
 *      3. onlineUser表，用userId为键，存放在线的客户端连接上下文；
 *      4. Group表，用groupId为键，存放群信息；
 *      5. User表，用username为键，存放用户信息。
 * @author dahuang
 * @version 1.0
 * @date 2021.4.26
 */
public class Constant {

	/**
	 * 保存客户端地登录Id
	 */
    public static final String USER_TOKEN = "userId";

	/**
	 * 用于存放已握手的实例
	 */
	public static Map<String, WebSocketServerHandshaker> webSocketHandshakerMap =
            new ConcurrentHashMap<String, WebSocketServerHandshaker>();

	/**
	 *  用于存放在线用户
	 */
	public static Map<String, ChannelHandlerContext> onlineUserMap =
	        new ConcurrentHashMap<String, ChannelHandlerContext>();

	//用于存放离线消息队列
	public static Map<String, List<String>> OfflineMessages =
			new ConcurrentHashMap<String, List<String>>();

}
