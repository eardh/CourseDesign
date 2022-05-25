package com.eardh.chatroom.service;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eardh.chatroom.Entity.MsgInfo;
import com.eardh.chatroom.utils.MessageState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

public class ChatService {

    private SocketChannel socketChannel;
    private static String nickname;
    private final List<MsgInfo> messages;
    private Handler handler;
    private Selector selector;
    private volatile boolean valid;
    private String ip;

    public ChatService(List<MsgInfo> messages, Handler handler, String ip) {
        this.messages = messages;
        this.handler = handler;
        this.ip = ip;
        valid = false;
    }

    public void working() {
        new Thread(() -> {
            startChatServer();
            readyReceive();
        }).start();
    }

    private void startChatServer() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(ip, 9527));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            valid = true;
            socketChannel.write(StandardCharsets.UTF_8.encode(nickname));
            handler.sendEmptyMessage(MessageState.CONNECT_SUCCESS);
        } catch (IOException e) {
            valid = false;
            handler.sendEmptyMessage(MessageState.CONNECT_FAILED);
        }
    }

    private void readyReceive() {
        new Thread(new Receiver(selector, this)).start();
    }

    public void sendMessage(String content) {
        if (!valid) {
            handler.sendEmptyMessage(MessageState.NOT_FIND_CONNECT);
            return;
        }
        new Thread(() -> {
            try {
                synchronized (messages) {
                    messages.add(new MsgInfo(nickname, content));
                }
                handler.sendEmptyMessage(MessageState.UPDATE_UI);
                socketChannel.write(StandardCharsets.UTF_8.encode(content));
                handler.sendEmptyMessage(MessageState.SEND_SUCCESS);
            } catch (IOException e) {
                valid = false;
                handler.sendEmptyMessage(MessageState.SERVER_EXCEPTION);
            }
        }).start();
    }

    public void read(SelectionKey selectionKey) {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            channel.read(buffer);
            buffer.flip();
            String s = StandardCharsets.UTF_8.decode(buffer).toString();
            chip_message(s);
            handler.sendEmptyMessage(MessageState.UPDATE_UI);
        } catch (IOException e) {
            selectionKey.cancel();
            valid = false;
            handler.sendEmptyMessage(MessageState.SERVER_EXCEPTION);
        }
    }

    public void chip_message(String s) {
        if (s.contains("\r\n\r\n")) {
            int i = s.indexOf("\r\n\r\n");
            String name = s.substring(0, i);
            String content = s.substring(i + 4);
            synchronized (messages) {
                messages.add(new MsgInfo(name, content, 1));
            }
        } else {
            synchronized (messages) {
                messages.add(new MsgInfo("server", s, 2));
            }
        }
    }

    public static void setNickname(String nickname) {
        ChatService.nickname = nickname;
    }

    public boolean isValid() {
        return valid;
    }

    public void close() {
        try {
            valid = false;
            if (socketChannel != null) {
                socketChannel.close();
            }
            if (selector != null) {
                selector.wakeup();
                selector.close();
            }
        } catch (IOException e) {
            handler.sendEmptyMessage(MessageState.UNKNOWN_ERROR);
        }
    }
}
