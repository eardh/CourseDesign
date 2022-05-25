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

public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private OnItemClickListener onItemClickListener;

    public GroupRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_person_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
        groupViewHolder.avatarView.setImageResource(R.drawable.avatar);
        groupViewHolder.nicknameView.setText(Constant.groups.get(position).getNickname());
    }

    @Override
    public int getItemCount() {
        return Constant.groups.size();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder{

        private ImageView avatarView;
        private TextView nicknameView;

        public GroupViewHolder(@NonNull View itemView) {
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