package com.eardh.wechat.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eardh.wechat.R;
import com.eardh.wechat.adapter.NewMessageAdapter;
import com.eardh.wechat.model.pojo.ChatMessage;
import com.eardh.wechat.page.ChatActivity;
import com.eardh.wechat.utils.Constant;
import com.eardh.wechat.utils.ListUtil;

public class MessageFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private Context context;

    private MessageFragment(Context context) {
        this.context = context;
    }

    public static MessageFragment newInstance(Context context) {
        return new MessageFragment(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"WrongConstant", "NotifyDataSetChanged"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_message, container, false);
            recyclerView = view.findViewById(R.id.fragment_message_recycle_view);
            Constant.newMessageAdapter = new NewMessageAdapter(context);
            Constant.newMessageAdapter.setOnItemClickListener(position -> {
                Intent intent = new Intent(context, ChatActivity.class);
                ChatMessage chatMessage = Constant.newMessage.get(position);
                if (chatMessage.isPersonal()) {
                    intent.addFlags(Constant.GO_CHAT_FRIEND_FRAME);
                    intent.putExtra("chat_obj", ListUtil.toPrivateChat(chatMessage.getID()));
                } else {
                    intent.addFlags(Constant.GO_CHAT_GROUP_FRAME);
                    intent.putExtra("chat_obj", ListUtil.toGroupChat(chatMessage.getID()));
                }
                chatMessage.setType(0);
                if (Constant.newMessageAdapter != null) {
                    Constant.newMessageAdapter.notifyDataSetChanged();
                }
                startActivity(intent);
            });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(Constant.newMessageAdapter);
        }
        return view;
    }
}