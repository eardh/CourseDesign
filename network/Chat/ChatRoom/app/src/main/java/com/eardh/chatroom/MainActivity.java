package com.eardh.chatroom;

import static com.eardh.chatroom.utils.ValidUtil.validIP;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button chat;
    private EditText ip_view;
    private EditText nickname_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chat = findViewById(R.id.chat);
        ip_view = findViewById(R.id.Ip);
        nickname_view = findViewById(R.id.set_nickname);
        chat.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat:
                startChatRoom();
                break;
        }
    }

    public void startChatRoom() {
        Intent intent = new Intent(this, ChatActivity.class);
        String ip = ip_view.getText().toString();
        String nickname = nickname_view.getText().toString();
        if (nickname.trim().length() == 0) {
            Toast.makeText(MainActivity.this, "输入合法昵称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!validIP(ip)) {
            Toast.makeText(MainActivity.this, "地址格式错误", Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra("ip", ip.trim());
        intent.putExtra("nickname", nickname.trim());
        startActivity(intent);
    }
}