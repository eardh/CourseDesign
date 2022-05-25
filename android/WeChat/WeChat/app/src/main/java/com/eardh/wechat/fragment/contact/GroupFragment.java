package com.eardh.wechat.fragment.contact;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eardh.wechat.R;
import com.eardh.wechat.adapter.FriendRecyclerViewAdapter;
import com.eardh.wechat.adapter.GroupRecyclerViewAdapter;
import com.eardh.wechat.model.vojo.EarResponse;
import com.eardh.wechat.page.ChatActivity;
import com.eardh.wechat.utils.Constant;

public class GroupFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private Context context;

    public GroupFragment(Context context) {
        this.context = context;
    }

    public static GroupFragment newInstance(Context context) {
        return new GroupFragment(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_group, container, false);
            recyclerView = view.findViewById(R.id.group_recyclerView);
            Constant.groupAdapter = new GroupRecyclerViewAdapter(context);
            Constant.groupAdapter.setOnItemClickListener(position -> {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.addFlags(Constant.GO_CHAT_GROUP_FRAME);
                intent.putExtra("chat_obj", Constant.groups.get(position));
                startActivity(intent);
            });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(Constant.groupAdapter);
        }
        return view;
    }
}