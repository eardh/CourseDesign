package com.eardh.wechat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eardh.wechat.R;
import com.eardh.wechat.utils.Constant;

public class FriendRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;

    public FriendRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_person_item, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FriendViewHolder friendHolder = (FriendViewHolder) holder;
        friendHolder.avatarView.setImageResource(R.drawable.avatar);
        friendHolder.nicknameView.setText(Constant.friends.get(position).getNickname());
    }

    @Override
    public int getItemCount() {
        return Constant.friends.size();
    }

    class FriendViewHolder extends RecyclerView.ViewHolder{

        private ImageView avatarView;
        private TextView nicknameView;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarView = itemView.findViewById(R.id.avatar_view);
            nicknameView = itemView.findViewById(R.id.nickname_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
