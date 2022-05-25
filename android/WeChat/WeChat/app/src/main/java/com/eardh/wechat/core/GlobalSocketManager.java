package com.eardh.wechat.core;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.eardh.wechat.handler.SocketHandler;
import com.eardh.wechat.model.vojo.EarResponse;
import com.eardh.wechat.utils.Constant;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class GlobalSocketManager {

    private static Selector selector;
    private static SocketChannel socketChannel;
    private static SocketHandler socketHandler;
    private volatile static GlobalSocketManager globalSocketManager;

    private GlobalSocketManager() {
        socketHandler = new SocketHandler();
    }

    public static GlobalSocketManager newInstance() {
        if (globalSocketManager == null) {
            synchronized (GlobalSocketManager.class) {
                if (globalSocketManager == null) {
                    globalSocketManager = new GlobalSocketManager();
                }
            }
        }
        return globalSocketManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public FutureTask<Boolean> connectServer() {
        FutureTask<Boolean> futureTask = new FutureTask<Boolean>(() -> {
            Message message = new Message();
            try {
                selector = Selector.open();
                socketChannel = SocketChannel.open(new InetSocketAddress("192.168.137.1", 9527));
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
                new Thread(new TcpClient()).start();
                message.what = Constant.CONNECTION_SUCCESS;
                message.obj = new EarResponse.Builder()
                        .setMsg("连接成功")
                        .builder();
                Constant.current.sendMessage(message);
                return true;
            } catch (IOException e) {
                socketChannel = null;
                message.what = Constant.CONNECTION_FAIL;
                message.obj = new EarResponse.Builder()
                        .setMsg("连接失败")
                        .builder();
                Constant.current.sendMessage(message);
                return false;
            }
        });
        new Thread(futureTask).start();
        return futureTask;
    }

    public static SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public static SocketHandler getSocketHandler() {
        return socketHandler;
    }

    public static Selector getSelector() {
        return selector;
    }

    public static GlobalSocketManager getGlobalSocketManager() {
        return globalSocketManager;
    }

    public static void closeAll() {
        try {
            if (socketChannel != null) {
                socketChannel.close();
                socketChannel = null;
            }
            if (selector != null) {
                selector.wakeup();
                selector.close();
                selector = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
