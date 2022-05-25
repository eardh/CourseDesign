package com.eardh.wechat.page;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eardh.wechat.R;
import com.eardh.wechat.adapter.MessageAdapter;
import com.eardh.wechat.core.GlobalSocketManager;
import com.eardh.wechat.handler.NotifyCallback;
import com.eardh.wechat.model.pojo.ChatMessage;
import com.eardh.wechat.model.pojo.Group;
import com.eardh.wechat.model.pojo.Message;
import com.eardh.wechat.model.pojo.User;
import com.eardh.wechat.model.vojo.EarRequest;
import com.eardh.wechat.utils.Constant;
import com.eardh.wechat.utils.ListUtil;
import com.eardh.wechat.utils.LoadingDialog;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatActivity extends AppCompatActivity {

    private TextView nickname_view;
    private EditText input_content_view;
    private Button send_msg_view, back_view_btn;
    private int chat_type;
    private String id;
    private Object chat_obj;
    private Handler handler;
    private LoadingDialog loadingDialog;
    private ListView listView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initData();
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("WrongViewCast")
    private void initView() {
        nickname_view = findViewById(R.id.chat_nickname_view);
        input_content_view = findViewById(R.id.content_view);
        send_msg_view = findViewById(R.id.send_msg_view);
        listView = findViewById(R.id.massage_view);
        back_view_btn = findViewById(R.id.chat_back_btn);

        if (chat_type == Constant.GO_CHAT_FRIEND_FRAME) {
            nickname_view.setText(((User) chat_obj).getNickname());
            id = ((User) chat_obj).getUserId();
        } else if (chat_type == Constant.GO_CHAT_GROUP_FRAME) {
            nickname_view.setText(((Group) chat_obj).getNickname());
            id = ((Group) chat_obj).getGroupId();
        }

        Constant.messageAdapter = new MessageAdapter(this, chat_obj);
        listView.setAdapter(Constant.messageAdapter);

        send_msg_view.setOnClickListener(v -> {
            String content = input_content_view.getText().toString().trim();
            if (!content.isEmpty()) {
                if (chat_obj instanceof User) {
                    privateSend(content);
                } else if (chat_obj instanceof Group) {
                    groupSend(content);
                }
            }
        });

        back_view_btn.setOnClickListener(v -> finish());

        loadingDialog = new LoadingDialog(this);
        handler = new Handler(new NotifyCallback(this, loadingDialog));
        Constant.current = handler;
    }

    private void initData() {
        Intent intent = getIntent();
        chat_type = intent.getFlags();
        chat_obj = intent.getSerializableExtra("chat_obj");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void privateSend(String content) {
        Message message = new Message(Constant.online_USER.getUserId(), ((User) chat_obj).getUserId(), 0, content);
        EarRequest request = new EarRequest.Builder()
                .setUrl("/user/chat/friend")
                .setData("message", message)
                .builder();
        new Thread(() -> {
            try {
                GlobalSocketManager.getSocketChannel().write(ByteBuffer.wrap(JSONObject.toJSONString(request).getBytes(StandardCharsets.UTF_8)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        ChatMessage chatMessage = new ChatMessage(Constant.online_USER.getUserId(), nickname_view.getText().toString(), 0, content, true);
        if (!Constant.global_map.containsKey(id)) {
            Constant.global_map.put(id, new CopyOnWriteArrayList<>());
        }
        List<ChatMessage> messageList = Constant.global_map.get(id);
        messageList.add(chatMessage);
        Constant.global_map.put(id, messageList);
        ChatMessage chatMessage1 = (ChatMessage) chatMessage.clone();
        chatMessage1.setID(((User)chat_obj).getUserId());
        if (ListUtil.checkAndReplace(chatMessage1)) {
            Constant.newMessage.add(chatMessage1);
        }
        Constant.newMessageAdapter.notifyDataSetChanged();
        Constant.messageAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void groupSend(String content) {
        Message message = new Message(Constant.online_USER.getUserId(), ((Group) chat_obj).getGroupId(), 0, content);
        EarRequest request = new EarRequest.Builder()
                .setUrl("/user/chat/group")
                .setData("message", message)
                .builder();
        new Thread(() -> {
            try {
                GlobalSocketManager.getSocketChannel().write(ByteBuffer.wrap(JSONObject.toJSONString(request).getBytes(StandardCharsets.UTF_8)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        ChatMessage chatMessage = new ChatMessage(Constant.online_USER.getUserId(), nickname_view.getText().toString(), 0, content, false);
        if (!Constant.global_map.containsKey(id)) {
            Constant.global_map.put(id, new CopyOnWriteArrayList<>());
        }
        if (!Constant.global_map.containsKey(id)) {
            Constant.global_map.put(id, new CopyOnWriteArrayList<>());
        }
        List<ChatMessage> messageList = Constant.global_map.get(id);
        messageList.add(chatMessage);
        Constant.global_map.put(id, messageList);
        ChatMessage chatMessage1 = (ChatMessage) chatMessage.clone();
        chatMessage1.setID(((Group)chat_obj).getGroupId());
        if (ListUtil.checkAndReplace(chatMessage1)) {
            Constant.newMessage.add(chatMessage1);
        }
        Constant.newMessageAdapter.notifyDataSetChanged();
        Constant.messageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.messageAdapter = null;
    }
}