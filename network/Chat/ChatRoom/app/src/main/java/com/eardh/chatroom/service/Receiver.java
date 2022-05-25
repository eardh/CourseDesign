package com.eardh.chatroom.service;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Receiver implements Runnable{

    private Selector selector;
    private ChatService chatService;

    public Receiver(Selector selector, ChatService chatService) {
        this.selector = selector;
        this.chatService = chatService;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        try {
            while (true) {
                int select = selector.select();
                if (!chatService.isValid()){
                    break;
                }
                if (select > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        if (selectionKey.isReadable()) {
                            chatService.read(selectionKey);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
