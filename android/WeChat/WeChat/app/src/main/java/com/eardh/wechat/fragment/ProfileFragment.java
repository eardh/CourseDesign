package com.eardh.wechat.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.eardh.wechat.R;
import com.eardh.wechat.core.GlobalSocketManager;
import com.eardh.wechat.model.vojo.EarRequest;
import com.eardh.wechat.utils.Constant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ProfileFragment extends Fragment {

    private View view;
    private TextView my_userId_view, my_nickname_view;
    private LinearLayout my_logout_view;
    private Context context;

    public ProfileFragment(Context context) {
        this.context = context;
    }

    public static ProfileFragment newInstance(Context context) {
        return new ProfileFragment(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_profile, container, false);
            my_userId_view = view.findViewById(R.id.my_userId_view);
            my_nickname_view = view.findViewById(R.id.my_nickname_view);
            my_logout_view = view.findViewById(R.id.my_logout_view);

            my_logout_view.setOnClickListener(v -> {
                EarRequest request = new EarRequest.Builder()
                        .setUrl("/user/logout")
                        .setData("ip", null)
                        .setData("id", Constant.online_USER.getUserId())
                        .builder();
                new Thread(() -> {
                    try {
                        GlobalSocketManager.getSocketChannel().write(StandardCharsets.UTF_8.encode(JSON.toJSONString(request)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            });
        }
        my_userId_view.setText(Constant.online_USER.getUserId());
        my_nickname_view.setText(Constant.online_USER.getNickname());
        return view;
    }
}