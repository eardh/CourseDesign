package com.dahuang.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.dahuang.mapper.GroupMapper;
import com.dahuang.mapper.UserMapper;
import com.dahuang.model.json.ResponseJson;
import com.dahuang.model.pojo.Group;
import com.dahuang.model.pojo.User;
import com.dahuang.service.ChatService;
import com.dahuang.utils.ChatType;
import com.dahuang.utils.Constant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 处理聊天业务
 */
@Service
public class ChatServiceImpl implements ChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServiceImpl.class);

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     *  登录成功后把用户注册进入 onlineUserMap
     * @param param
     * @param ctx
     */
    @Override
    public void register(JSONObject param, ChannelHandlerContext ctx) {
        String userId = (String)param.get("userId");
        Constant.onlineUserMap.put(userId, ctx);
        String responseJson = new ResponseJson().success()
                .setData("type", ChatType.REGISTER)
                .toString();
        sendMessage(ctx, responseJson);


        // 离线消息加载处理
        List<String> messageList = Constant.OfflineMessages.get(userId);
        if (messageList != null) {
            // 集合进行删除时必须要用迭代器，因为会报错
            Iterator<String> it = messageList.iterator();
            while (it.hasNext()){
                String next = it.next();
                sendMessage(ctx,next);
                it.remove();
            }
            Constant.OfflineMessages.put(userId,messageList);
        }

        LOGGER.info(MessageFormat.format("userId为 {0} 的用户登记到在线用户表，当前在线人数为：{1}"
                , userId, Constant.onlineUserMap.size()));
    }

    /**
     *  单发送消息
     * @param param
     * @param ctx
     */
    @Override
    public void singleSend(JSONObject param, ChannelHandlerContext ctx) {
        String fromUserId = (String)param.get("fromUserId");
        String toUserId = (String)param.get("toUserId");
        String content = (String)param.get("content");
        ChannelHandlerContext toUserCtx = Constant.onlineUserMap.get(toUserId);

        String fromAvatarUrl = userMapper.getByUserId(fromUserId).getAvatarUrl();

        String responseJson = new ResponseJson().success()
                .setData("fromUserId", fromUserId)
                .setData("content", content)
                .setData("type", ChatType.SINGLE_SENDING)
                .setData("fromAvatarUrl",fromAvatarUrl)
                .toString();

        //  离线消息处理
        if (toUserCtx == null) {
            List<String> list = Constant.OfflineMessages.get(toUserId);
            if(list == null){
                list = new ArrayList<>();
            }
            list.add(responseJson);
            Constant.OfflineMessages.put(toUserId,list);
        } else {
            sendMessage(toUserCtx, responseJson);
        }
    }

    /**
     *  群发消息
     * @param param
     * @param ctx
     */
    @Override
    public void groupSend(JSONObject param, ChannelHandlerContext ctx) {

        String fromUserId = (String)param.get("fromUserId");
        String toGroupId = (String)param.get("toGroupId");
        String content = (String)param.get("content");

        // 发送者信息
        User fromUser = userMapper.getByUserId(fromUserId);

        Group group = groupMapper.getByGroupId(toGroupId);
        List<User> members = groupMapper.getUsersByGroupId(toGroupId);
        group.setMembers(members);

        if (group == null) {
            String responseJson = new ResponseJson().error("该群id不存在").toString();
            sendMessage(ctx, responseJson);
        } else {
            String responseJson = new ResponseJson().success()
                    .setData("fromUserId", fromUserId)
                    .setData("content", content)
                    .setData("toGroupId", toGroupId)
                    .setData("type", ChatType.GROUP_SENDING)
                    .setData("fromAvatarUrl",fromUser.getAvatarUrl())
                    .setData("fromUserName",fromUser.getUsername())
                    .toString();

            //消息群发处理
            group.getMembers().stream()
                .forEach(member -> {
                    ChannelHandlerContext toCtx = Constant.onlineUserMap.get(member.getUserId());

                    // 离线消息处理
                    if(toCtx == null) {
                        List<String> list = Constant.OfflineMessages.get(member.getUserId());
                        if(list == null){
                            list = new ArrayList<>();
                        }
                        list.add(responseJson);
                        Constant.OfflineMessages.put(member.getUserId(),list);
                    } else if (toCtx != null && !member.getUserId().equals(fromUserId)) {
                        sendMessage(toCtx, responseJson);
                    }
                });
        }
    }

    /**
     *  用户注销或退出后移除该在线用户
     * @param ctx
     */
    @Override
    public void remove(ChannelHandlerContext ctx) {
        Iterator<Entry<String, ChannelHandlerContext>> iterator =
                Constant.onlineUserMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Entry<String, ChannelHandlerContext> entry = iterator.next();
            if (entry.getValue() == ctx) {
                LOGGER.info("正在移除握手实例...");
                Constant.webSocketHandshakerMap.remove(ctx.channel().id().asLongText());
                LOGGER.info(MessageFormat.format("已移除握手实例，当前握手实例总数为：{0}"
                        , Constant.webSocketHandshakerMap.size()));
                iterator.remove();
                LOGGER.info(MessageFormat.format("userId为 {0} 的用户已退出聊天，当前在线人数为：{1}"
                        , entry.getKey(), Constant.onlineUserMap.size()));
                break;
            }
        }
    }


    /**
     *  单独发送文件
     * @param param
     * @param ctx
     */
    @Override
    public void FileMsgSingleSend(JSONObject param, ChannelHandlerContext ctx) {
        String fromUserId = (String)param.get("fromUserId");
        String toUserId = (String)param.get("toUserId");
        String originalFilename = (String)param.get("originalFilename");
        String fileSize = (String)param.get("fileSize");
        String fileUrl = (String)param.get("fileUrl");

        String fromAvatarUrl = userMapper.getByUserId(fromUserId).getAvatarUrl();

        ChannelHandlerContext toUserCtx = Constant.onlineUserMap.get(toUserId);

        // 消息
        String responseJson = new ResponseJson().success()
                .setData("fromUserId", fromUserId)
                .setData("originalFilename", originalFilename)
                .setData("fileSize", fileSize)
                .setData("fileUrl", fileUrl)
                .setData("type", ChatType.FILE_MSG_SINGLE_SENDING)
                .setData("fromAvatarUrl",fromAvatarUrl)
                .toString();

        //离线消息处理
        if (toUserCtx == null) {
            List<String> list = Constant.OfflineMessages.get(toUserId);
            if(list == null){
                list = new ArrayList<>();
            }
            list.add(responseJson);
            Constant.OfflineMessages.put(toUserId,list);
        } else {
            sendMessage(toUserCtx, responseJson);
        }
    }

    /**
     *  群发文件处理
     * @param param
     * @param ctx
     */
    @Override
    public void FileMsgGroupSend(JSONObject param, ChannelHandlerContext ctx) {
        String fromUserId = (String)param.get("fromUserId");
        String toGroupId = (String)param.get("toGroupId");
        String originalFilename = (String)param.get("originalFilename");
        String fileSize = (String)param.get("fileSize");
        String fileUrl = (String)param.get("fileUrl");

        Group group = groupMapper.getByGroupId(toGroupId);
        List<User> members = groupMapper.getUsersByGroupId(toGroupId);
        group.setMembers(members);

        // 发送者信息
        User fromUser = userMapper.getByUserId(fromUserId);

        if (group == null) {
            String responseJson = new ResponseJson().error("该群id不存在").toString();
            sendMessage(ctx, responseJson);
        } else {
            String responseJson = new ResponseJson().success()
                    .setData("fromUserId", fromUserId)
                    .setData("toGroupId", toGroupId)
                    .setData("originalFilename", originalFilename)
                    .setData("fileSize", fileSize)
                    .setData("fileUrl", fileUrl)
                    .setData("type", ChatType.FILE_MSG_GROUP_SENDING)
                    .setData("fromAvatarUrl",fromUser.getAvatarUrl())
                    .setData("fromUserName",fromUser.getUsername())
                    .toString();
            group.getMembers().stream()
                .forEach(member -> {
                    ChannelHandlerContext toCtx = Constant.onlineUserMap.get(member.getUserId());

                    //离线消息处理
                    if(toCtx == null) {
                        List<String> list = Constant.OfflineMessages.get(member.getUserId());
                        if(list == null){
                            list = new ArrayList<>();
                        }
                        list.add(responseJson);
                        Constant.OfflineMessages.put(member.getUserId(),list);
                    } else if (toCtx != null && !member.getUserId().equals(fromUserId)) {
                        sendMessage(toCtx, responseJson);
                    }
                });
        }
    }

    /**
     *  文件类型错误处理
     * @param ctx
     */
    @Override
    public void typeError(ChannelHandlerContext ctx) {
        String responseJson = new ResponseJson()
                .error("该类型不存在！")
                .toString();
        sendMessage(ctx, responseJson);
    }


    /**
     *  发送消息类
     * @param ctx
     * @param message
     */
    private void sendMessage(ChannelHandlerContext ctx, String message) {
        ctx.channel().writeAndFlush(new TextWebSocketFrame(message));
    }

}
