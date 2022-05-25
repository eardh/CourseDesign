package com.eardh.wechat.core;

import android.util.Log;

import com.eardh.wechat.handler.SocketHandler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class TcpClient implements Runnable{

    private Selector selector;
    private SocketHandler handler;

    public TcpClient() {
        this.selector = GlobalSocketManager.getSelector();
        this.handler = GlobalSocketManager.getSocketHandler();
    }

    @Override
    public void run() {
        try {
            while (true) {
                int select = selector.select();
                if (select == 0) {
                    break;
                }
                if (select > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        if (selectionKey.isReadable()) {
                            handler.read(selector, selectionKey);
                        }
                    }
                }
            }
            Log.i("9527", 1+"423");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
