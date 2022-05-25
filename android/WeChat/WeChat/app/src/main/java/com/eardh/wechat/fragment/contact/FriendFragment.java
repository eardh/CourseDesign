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
import com.eardh.wechat.adapter.OnItemClickListener;
import com.eardh.wechat.model.vojo.EarResponse;
import com.eardh.wechat.page.ChatActivity;
import com.eardh.wechat.utils.Constant;

public class FriendFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private Context context;

    public FriendFragment(Context context) {
        this.context = context;
    }

    public static FriendFragment newInstance(Context context) {
        return new FriendFragment(context);
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
            view = inflater.inflate(R.layout.fragment_friend, container, false);
            recyclerView = view.findViewById(R.id.friend_recyclerView);
            Constant.friendAdapter = new FriendRecyclerViewAdapter(context);
            Constant.friendAdapter.setOnItemClickListener(position -> {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.addFlags(Constant.GO_CHAT_FRIEND_FRAME);
                intent.putExtra("chat_obj", Constant.friends.get(position));
                startActivity(intent);
            });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(Constant.friendAdapter);
        }
        return view;
    }
}