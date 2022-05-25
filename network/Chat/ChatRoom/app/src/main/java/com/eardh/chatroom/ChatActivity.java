package com.eardh.chatroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.eardh.chatroom.Entity.MsgInfo;
import com.eardh.chatroom.adapter.MsgAdapter;
import com.eardh.chatroom.service.ChatService;
import com.eardh.chatroom.utils.MessageState;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private Button send_btn;
    private EditText content_view;

    private ListView msg_view;
    private MsgAdapter adapter;
    private ChatService chatService;


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull android.os.Message msg) {
            switch (msg.what) {
                case MessageState.CONNECT_FAILED:
                    Toast.makeText(ChatActivity.this, "fail to connect server", Toast.LENGTH_SHORT).show();
                    break;
                case MessageState.UPDATE_UI:
                    adapter.notifyDataSetChanged();
                    msg_view.smoothScrollToPosition(adapter.getCount());
                    break;
                case MessageState.SERVER_EXCEPTION:
                    Toast.makeText(ChatActivity.this, "server exception", Toast.LENGTH_SHORT).show();
                    break;
                case MessageState.CONNECT_SUCCESS:
                    Toast.makeText(ChatActivity.this, "succeed to connect server", Toast.LENGTH_SHORT).show();
                    break;
                case MessageState.SEND_SUCCESS:
                    content_view.setText("");
                    break;
                case MessageState.UNKNOWN_ERROR:
                    Toast.makeText(ChatActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        init(intent.getStringExtra("ip"), intent.getStringExtra("nickname"));
    }


    private void init(String ip, String nickname) {
        send_btn = findViewById(R.id.send);
        content_view = findViewById(R.id.info);
        send_btn.setOnClickListener(this);
        List<MsgInfo> messages = new ArrayList<>();
        msg_view = findViewById(R.id.massage_view);

        adapter = new MsgAdapter(this, messages);
        chatService = new ChatService(messages, handler, ip);
        ChatService.setNickname(nickname);
        msg_view.setAdapter(adapter);
        chatService.working();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                String content = content_view.getText().toString();
                if (content.trim().length() == 0) {
                    Toast.makeText(ChatActivity.this, "The message is empty or blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                chatService.sendMessage(content);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        chatService.close();
    }
}