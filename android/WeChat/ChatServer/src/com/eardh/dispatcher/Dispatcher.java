package com.eardh.dispatcher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eardh.annotation.EarParam;
import com.eardh.model.pojo.Message;
import com.eardh.model.vojo.EarRequest;
import com.eardh.model.vojo.EarResponse;
import com.eardh.utils.Constant;
import com.eardh.utils.ConstantPool;
import com.eardh.utils.StringUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Dispatcher {

    public void accept(Selector selector, SelectionKey selectionKey) {
        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = null;
        try {
            socketChannel = channel.accept();
            if (socketChannel != null) {
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
            }
        } catch (IOException e) {
            if (socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void read(Selector selector, SelectionKey selectionKey) {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            SocketAddress address = channel.getRemoteAddress();
            int read = channel.read(buffer);
            System.out.println(read);
            buffer.flip();
            if (read != -1) {
                EarRequest request = JSON.parseObject(String.valueOf(StandardCharsets.UTF_8.decode(buffer)), EarRequest.class);
                EarResponse response = dispatch(address, request);
                send(selector, selectionKey, response);
            } else {
                EarRequest request = new EarRequest.Builder()
                        .setUrl("/user/logout")
                        .setData("id", null)
                        .setData("ip", address)
                        .builder();
                dispatch(address, request);
                throw new IOException();
            }
        } catch (IOException e) {
            selectionKey.cancel();
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void send(Selector selector, SelectionKey selectionKey, EarResponse response) throws IOException {
        if (response.getCode() == Constant.FRIENDS_MESSAGE) {
            String ip = (String) response.getBody().get("ip");
            Set<SelectionKey> keys = selector.keys();
            for (SelectionKey key : keys) {
                SelectableChannel channel = key.channel();
                if (key.isValid() && channel instanceof SocketChannel) {
                    SocketAddress address = ((SocketChannel) channel).getRemoteAddress();
                    if (address.toString().equals(ip)) {
                        ((SocketChannel) channel).write(ByteBuffer.wrap(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8)));
                    }
                }
            }
        } else if (response.getCode() == Constant.GROUP_MESSAGE) {
            List<String> ips = (List<String>) response.getBody().get("ips");
            Set<SelectionKey> keys = selector.keys();
            SocketChannel self = (SocketChannel) selectionKey.channel();
            for (SelectionKey key : keys) {
                SelectableChannel channel = key.channel();
                if (key.isValid() && channel instanceof SocketChannel && channel != self) {
                    SocketAddress address = ((SocketChannel) channel).getRemoteAddress();
                    if (ips.contains(address.toString())) {
                        ((SocketChannel) channel).write(ByteBuffer.wrap(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8)));
                    }
                }
            }
        } else {
            SocketChannel channel = (SocketChannel) selectionKey.channel();
            selector.selectedKeys();
            channel.write(ByteBuffer.wrap(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8)));
        }
    }

    public EarResponse dispatch(SocketAddress address, EarRequest request) throws InvocationTargetException, IllegalAccessException {
        String url = request.getUrl();
        String prefix = StringUtil.urlPrefix(url);
        Method method = ConstantPool.methodMap.get(url);
        Object o = ConstantPool.objectMap.get(prefix);
        Annotation[][] annotations = ConstantPool.paramMap.get(url);
        request.getParams().put("ip", address.toString());
        List<Object> a = new ArrayList<>();
        for (Annotation[] annotation : annotations) {
            for (Annotation annotation1 : annotation) {
                EarParam earParam = (EarParam) annotation1;
                if (url.contains("chat")) {
                    a.add(JSONObject.parseObject(request.getParams().get(earParam.value()).toString(), Message.class));
                } else {
                    a.add(request.getParams().get(earParam.value()));
                }
            }
        }
        return (EarResponse) method.invoke(o, a.toArray());
    }
}
