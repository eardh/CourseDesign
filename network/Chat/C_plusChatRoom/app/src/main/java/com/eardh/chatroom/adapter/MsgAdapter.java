package com.eardh.chatroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eardh.chatroom.Entity.MsgInfo;
import com.eardh.chatroom.R;

import java.util.List;

public class MsgAdapter extends BaseAdapter {

    private Context context;
    List<MsgInfo> msg;

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    public MsgAdapter(Context context, List<MsgInfo> msg) {
        this.context = context;
        this.msg = msg;
    }

    @Override
    public int getCount() {
        return msg.size();
    }

    @Override
    public Object getItem(int position) {
        return msg.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        MsgInfo msgInfo = (MsgInfo) getItem(position);
        switch (getItemViewType(position)) {
            case 0:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.msg_type_item, parent, false);
                    holder = new Holder();
                    holder.avatar = convertView.findViewById(R.id.avatar);
                    holder.content = convertView.findViewById(R.id.content);
                    convertView.setTag(holder);
                }
                break;
            case 1:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.msg_others_item, parent, false);
                    holder = new Holder();
                    holder.avatar = convertView.findViewById(R.id.avatar);
                    holder.nickname = convertView.findViewById(R.id.nickname);
                    holder.content = convertView.findViewById(R.id.content);
                    convertView.setTag(holder);
                }
                break;
            case 2:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.msg_remind_item, parent, false);
                    holder = new Holder();
                    holder.content = convertView.findViewById(R.id.remind);
                    convertView.setTag(holder);
                }
                holder = (Holder) convertView.getTag();
                holder.content.setText(msgInfo.getContent());
                return convertView;
        }
        holder = (Holder) convertView.getTag();
        holder.avatar.setImageResource(R.drawable.avatar);
        if (getItemViewType(position) == 1) {
            holder.nickname.setText((msgInfo.getName()));
        }
        holder.content.setText(msgInfo.getContent());
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        int type = msg.get(position).getType();
        return type;
    }

    class Holder {
        ImageView avatar;
        TextView nickname;
        TextView content;
    }
}