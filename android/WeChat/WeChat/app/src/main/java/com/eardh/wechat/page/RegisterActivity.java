package com.eardh.wechat.page;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.eardh.wechat.R;
import com.eardh.wechat.core.GlobalSocketManager;
import com.eardh.wechat.handler.NotifyCallback;
import com.eardh.wechat.model.vojo.EarRequest;
import com.eardh.wechat.utils.Constant;
import com.eardh.wechat.utils.LoadingDialog;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class RegisterActivity extends AppCompatActivity {

    private TextView nickname_view, password_view;
    private Button register_btn;
    private LoadingDialog loadingDialog;
    public Handler handler;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initView() {
        nickname_view = findViewById(R.id.register_nickname_view);
        password_view = findViewById(R.id.register_password_view);
        register_btn = findViewById(R.id.register_btn_view);


        loadingDialog = new LoadingDialog(this);
        NotifyCallback callback = new NotifyCallback(this, loadingDialog);
        handler = new Handler(callback);
        Constant.current = handler;

        register_btn.setOnClickListener(v -> {
            String nickname = nickname_view.getText().toString().trim();
            String password = password_view.getText().toString().trim();
            if (nickname.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "昵称或密码不为空", Toast.LENGTH_SHORT).show();
            }
            EarRequest request = new EarRequest.Builder()
                    .setUrl("/user/register")
                    .setData("nickname", nickname)
                    .setData("password", password)
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
    }
}