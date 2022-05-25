package com.eardh.wechat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eardh.wechat.R;
import com.eardh.wechat.model.pojo.ChatMessage;
import com.eardh.wechat.utils.Constant;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NewMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private OnItemClickListener onItemClickListener;
    private List<ChatMessage> messages;

    public NewMessageAdapter(Context context) {
        this.context = context;
        messages = Constant.newMessage;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_message_item, parent, false);
        if (viewType == 1) {
            view.setBackgroundColor(R.color.new_message);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageViewHolder messageViewHolder = (MessageViewHolder) holder;
        messageViewHolder.avatarView.setImageResource(R.drawable.avatar);
        messageViewHolder.nicknameView.setText(messages.get(position).getNickname());
        messageViewHolder.contentView.setText(messages.get(position).getContent());
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{

        private ImageView avatarView;
        private TextView nicknameView;
        private TextView contentView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarView = itemView.findViewById(R.id.new_avatar_view);
            nicknameView = itemView.findViewById(R.id.new_nickname_view);
            contentView = itemView.findViewById(R.id.new_message_content);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}