package com.dahuang.config;

import com.dahuang.web.handler.WebSocketChildChannelHandler;
import com.dahuang.web.websocket.WebSocketServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 *  注册启动是需要地 Bean
 */
@Configuration
public class NettyConfig {

    @Bean
    public WebSocketServer getWebSocketServer(){
        return new WebSocketServer();
    }

    @Bean
    public NioEventLoopGroup getNioEventLoopGroup(){
        return new NioEventLoopGroup();
    }

    @Bean
    @Scope("prototype")
    public ServerBootstrap getServerBootstrap(){
        return new ServerBootstrap();
    }

    @Bean(name = "childChannelHandler")
    public WebSocketChildChannelHandler getChildChannelHandler(){
        return new WebSocketChildChannelHandler();
    }
}
