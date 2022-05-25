package com.eardh.wechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.eardh.wechat.adapter.FragmentAdapter;
import com.eardh.wechat.core.GlobalSocketManager;
import com.eardh.wechat.fragment.MessageFragment;
import com.eardh.wechat.fragment.ContactPersonFragment;
import com.eardh.wechat.fragment.ProfileFragment;
import com.eardh.wechat.handler.NotifyCallback;
import com.eardh.wechat.model.vojo.EarRequest;
import com.eardh.wechat.model.vojo.EarResponse;
import com.eardh.wechat.page.LoginActivity;
import com.eardh.wechat.utils.Constant;
import com.eardh.wechat.utils.LoadingDialog;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager2 viewPager;
    private LinearLayout l_tab_message, l_tab_contact_person, l_tab_profile;
    private ImageView v_tab_message_img, v_tab_contact_person_img, v_profile_img, v_current;

    private LoadingDialog loadingDialog;
    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabView();
        initPager();
        initData();
    }

    private void initData() {
        loadingDialog = new LoadingDialog(this);
        handler = new Handler(new NotifyCallback(this, loadingDialog));
        Constant.current = handler;
        new Thread(() -> {
            EarRequest request1 = new EarRequest.Builder()
                    .setUrl("/user/friends")
                    .setData("userId", Constant.online_USER.getUserId())
                    .builder();
            EarRequest request2 = new EarRequest.Builder()
                    .setUrl("/user/groups")
                    .setData("userId", Constant.online_USER.getUserId())
                    .builder();
            try {
                GlobalSocketManager.getSocketChannel().write(StandardCharsets.UTF_8.encode(JSON.toJSONString(request1)));
                GlobalSocketManager.getSocketChannel().write(StandardCharsets.UTF_8.encode(JSON.toJSONString(request2)));
            } catch (IOException e) {
                Message message = new Message();
                message.what = Constant.REQUEST_FAIL;
                message.obj = new EarResponse();
                Constant.current.sendMessage(message);
                e.printStackTrace();
            }
        }).start();
    }

    private void initTabView() {
        l_tab_message = findViewById(R.id.tab_message);
        l_tab_message.setOnClickListener(this);
        l_tab_contact_person = findViewById(R.id.tab_contact_person);
        l_tab_contact_person.setOnClickListener(this);
        l_tab_profile = findViewById(R.id.tab_profile);
        l_tab_profile.setOnClickListener(this);

        v_tab_message_img = findViewById(R.id.tab_message_img);
        v_tab_contact_person_img = findViewById(R.id.tab_contact_person_img);
        v_profile_img = findViewById(R.id.tab_profile_img);

        v_tab_message_img.setSelected(true);
        v_current = v_tab_message_img;

        loadingDialog = new LoadingDialog(this);
        NotifyCallback callback = new NotifyCallback(this, loadingDialog);
        handler = new Handler(callback);
        Constant.current = handler;
    }

    private void initPager() {
        viewPager = findViewById(R.id.view_pager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MessageFragment.newInstance(this));
        fragments.add(ContactPersonFragment.newInstance(this));
        fragments.add(ProfileFragment.newInstance(this));
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                changeTab(position);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void changeTab(int position) {
        v_current.setSelected(false);
        switch (position) {
            case R.id.tab_message:
                viewPager.setCurrentItem(0, false);
            case 0:
                v_tab_message_img.setSelected(true);
                v_current = v_tab_message_img;
                break;
            case R.id.tab_contact_person:
                viewPager.setCurrentItem(1, false);
            case 1:
                v_tab_contact_person_img.setSelected(true);
                v_current = v_tab_contact_person_img;
                break;
            case R.id.tab_profile:
                viewPager.setCurrentItem(2, false);
            case 2:
                v_profile_img.setSelected(true);
                v_current = v_profile_img;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        changeTab(v.getId());
    }

    @Override
    protected void onDestroy() {
        Constant.clear();
        GlobalSocketManager.closeAll();
        super.onDestroy();
    }
}