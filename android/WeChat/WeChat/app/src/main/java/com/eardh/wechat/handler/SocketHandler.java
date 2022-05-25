package com.eardh.wechat.handler;

import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eardh.wechat.core.GlobalSocketManager;
import com.eardh.wechat.model.vojo.EarRequest;
import com.eardh.wechat.model.vojo.EarResponse;
import com.eardh.wechat.utils.Constant;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class SocketHandler {

    public void read(Selector selector, SelectionKey selectionKey) {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            channel.read(buffer);
            buffer.flip();
            EarResponse response = JSONObject.parseObject(StandardCharsets.UTF_8.decode(buffer).toString(), EarResponse.class);
            dispatch(response);
        } catch (IOException e) {
            e.printStackTrace();
            selectionKey.cancel();
        }
    }

    public void dispatch(EarResponse response) {
        Message message = new Message();
        message.what = response.getCode();
        message.obj = response;
        Constant.current.sendMessage(message);
    }
}
