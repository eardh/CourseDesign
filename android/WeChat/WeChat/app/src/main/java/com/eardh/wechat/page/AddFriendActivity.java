package com.eardh.wechat.page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.eardh.wechat.R;
import com.eardh.wechat.core.GlobalSocketManager;
import com.eardh.wechat.handler.NotifyCallback;
import com.eardh.wechat.model.vojo.EarRequest;
import com.eardh.wechat.utils.Constant;
import com.eardh.wechat.utils.LoadingDialog;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AddFriendActivity extends AppCompatActivity {

    private EditText person_earID_view;
    private Button add_friend_btn_view, add_group_btn_view;
    private Handler handler;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        initView();
    }

    private void initView() {

        person_earID_view = findViewById(R.id.userId_edt_view);
        add_friend_btn_view = findViewById(R.id.add_friend_btn_view);
        add_group_btn_view = findViewById(R.id.add_group_btn_view);

        add_friend_btn_view.setOnClickListener(v -> {
            EarRequest request = new EarRequest.Builder()
                    .setUrl("/user/add")
                    .setData("userId", Constant.online_USER.getUserId())
                    .setData("fuserId", person_earID_view.getText().toString().trim())
                    .builder();
            loadingDialog.show();
            new Thread(() -> {
                try {
                    GlobalSocketManager.getSocketChannel().write(StandardCharsets.UTF_8.encode(JSON.toJSONString(request)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        });

        add_group_btn_view.setOnClickListener(v -> {
            EarRequest request = new EarRequest.Builder()
                    .setUrl("/group/add")
                    .setData("userId", Constant.online_USER.getUserId())
                    .setData("groupId", person_earID_view.getText().toString().trim())
                    .builder();
            loadingDialog.show();
            new Thread(() -> {
                try {
                    GlobalSocketManager.getSocketChannel().write(StandardCharsets.UTF_8.encode(JSON.toJSONString(request)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        });

        loadingDialog = new LoadingDialog(this);
        handler = new Handler(new NotifyCallback(this, loadingDialog));
        Constant.current = handler;
    }
}