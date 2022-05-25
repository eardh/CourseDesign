package com.eardh.core;

import com.eardh.dispatcher.Dispatcher;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;

public class TcpServer implements Runnable{

    private int port;
    private Dispatcher dispatcher;

    public TcpServer(int port, Dispatcher dispatcher) {
        this.port = port;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        try (ServerSocketChannel socketChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {
            socketChannel.bind(new InetSocketAddress(port)); // 绑定本地端口号
            socketChannel.configureBlocking(false); // 设置非阻塞模式
            socketChannel.register(selector, SelectionKey.OP_ACCEPT); // 为ServerSocketChannel注册兴趣事件
            while (true) {
                if (selector.select() > 0) { // 阻塞等待注册 事件发生
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        if (!selectionKey.isValid()) {
                            continue;
                        }
                        if (selectionKey.isAcceptable()) {
                            dispatcher.accept(selector, selectionKey);
                        }
                        if (selectionKey.isReadable()) {
                            dispatcher.read(selector, selectionKey);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
