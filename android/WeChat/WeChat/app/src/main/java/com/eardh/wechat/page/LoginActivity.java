package com.eardh.wechat.page;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.eardh.wechat.R;
import com.eardh.wechat.core.GlobalSocketManager;
import com.eardh.wechat.core.TcpClient;
import com.eardh.wechat.handler.NotifyCallback;
import com.eardh.wechat.handler.SocketHandler;
import com.eardh.wechat.model.vojo.EarRequest;
import com.eardh.wechat.utils.Constant;
import com.eardh.wechat.utils.LoadingDialog;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class LoginActivity extends AppCompatActivity {

    private EditText userId_view;
    private EditText password_view;
    private Button login_view, register_view;
    private LoadingDialog loadingDialog;
    public Handler handler;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponent();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initComponent() {
        userId_view = findViewById(R.id.userId_view);
        password_view = findViewById(R.id.password_view);
        login_view = findViewById(R.id.login_view);
        register_view = findViewById(R.id.register_view);

        loadingDialog = new LoadingDialog(this);
        NotifyCallback callback = new NotifyCallback(this, loadingDialog);
        handler = new Handler(callback);
        Constant.current = handler;

        login_view.setOnClickListener(v -> {
            EarRequest request = new EarRequest.Builder()
                    .setUrl("/user/login")
                    .setData("userId", userId_view.getText().toString().trim())
                    .setData("password", password_view.getText().toString().trim())
                    .builder();
            loadingDialog.show();
            new Thread(() -> {
                try {
                    if (GlobalSocketManager.getSocketChannel() == null) {
                        FutureTask<Boolean> futureTask = GlobalSocketManager.newInstance().connectServer();
                        if (futureTask.get()) {
                            GlobalSocketManager.getSocketChannel().write(StandardCharsets.UTF_8.encode(JSON.toJSONString(request)));
                        }
                    } else {
                        GlobalSocketManager.getSocketChannel().write(StandardCharsets.UTF_8.encode(JSON.toJSONString(request)));
                    }
                } catch (IOException e) {
                    handler.sendEmptyMessage(Constant.REQUEST_FAIL);
                    e.printStackTrace();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }).start();
        });

        register_view.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

    }
}