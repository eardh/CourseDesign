package com.eardh.chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client implements Runnable{

    @Override
    public void run() {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("192.168.43.107", 9527));
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String readLine = reader.readLine();
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(readLine.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}