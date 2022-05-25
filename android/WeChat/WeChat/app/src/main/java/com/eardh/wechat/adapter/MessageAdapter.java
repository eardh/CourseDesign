package com.eardh.wechat.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.eardh.wechat.R;
import com.eardh.wechat.model.pojo.ChatMessage;
import com.eardh.wechat.model.pojo.Group;
import com.eardh.wechat.model.pojo.User;
import com.eardh.wechat.utils.Constant;

import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<ChatMessage> messages;
    private Object object;

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MessageAdapter(Context context, Object obj) {
        this.context = context;
        object = obj;
        String id;
        if (obj instanceof User) {
            id = ((User) obj).getUserId();
            if (!Constant.global_map.containsKey(id)) {
                Constant.global_map.put(id, new CopyOnWriteArrayList<>());
            }
            this.messages = Constant.global_map.get(id);
        } else {
            id = ((Group) obj).getGroupId();
            if (!Constant.global_map.containsKey(id)) {
                Constant.global_map.put(id, new CopyOnWriteArrayList<>());
            }
            this.messages = Constant.global_map.get(id);        }
        Constant.global_map.put(id, messages);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        ChatMessage message = (ChatMessage) getItem(position);
        switch (getItemViewType(position)) {
            case 0:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.msg_self_item, parent, false);
                    holder = new Holder();
                    holder.avatar = convertView.findViewById(R.id.avatar);
                    holder.content = convertView.findViewById(R.id.content);
                    convertView.setTag(holder);
                }
                break;
            case 1:
                if (convertView == null) {
                    if (object instanceof User) {
                        convertView = LayoutInflater.from(context).inflate(R.layout.msg_private_others_item, parent, false);
                        holder = new Holder();
                        holder.avatar = convertView.findViewById(R.id.avatar);
                        holder.content = convertView.findViewById(R.id.content);
                    } else if (object instanceof Group) {
                        convertView = LayoutInflater.from(context).inflate(R.layout.msg_goup_others_item, parent, false);
                        holder = new Holder();
                        holder.avatar = convertView.findViewById(R.id.avatar);
                        holder.nickname = convertView.findViewById(R.id.nickname);
                        holder.content = convertView.findViewById(R.id.content);
                    }
                    convertView.setTag(holder);
                }
                break;
        }
        holder = (Holder) convertView.getTag();
        if (getItemViewType(position) == 1) {
            if (holder.nickname != null) {
                holder.nickname.setText(messages.get(position).getNickname());
            }
            holder.avatar.setImageResource(R.mipmap.ic_launcher_round);
        } else {
            holder.avatar.setImageResource(R.mipmap.ic_launcher_round);
        }
        holder.content.setText(message.getContent());
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        int type = messages.get(position).getType();
        return type;
    }

    class Holder {
        ImageView avatar;
        TextView nickname;
        TextView content;
    }
}