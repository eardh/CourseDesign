package com.dahuang.service.Impl;

import com.dahuang.mapper.GroupMapper;
import com.dahuang.mapper.UserMapper;
import com.dahuang.model.json.ResponseJson;
import com.dahuang.model.pojo.Group;
import com.dahuang.model.pojo.User;
import com.dahuang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public ResponseJson getByUserId(String userId) {

        //获取用户信息
        User user = userMapper.getByUserId(userId);

        //获取好友列表信息
        user.setFriendList(userMapper.getFriendsByUserId(userId));

         //得到群组Id
        List<String> groupIds = userMapper.getGroupIdsByUserId(userId);

        //存放群组其他成员
        List<Group> groupList = new ArrayList<>();

        for(String groupId : groupIds){

            //获得群组成员
            List<User> members = groupMapper.getUsersByGroupId(groupId);

            //获得群组基本信息
            Group group = groupMapper.getByGroupId(groupId);

            group.setMembers(members);

            groupList.add(group);

        }

        // 关联群
        user.setGroupList(groupList);

        return new ResponseJson().success()
                .setData("user", user);
    }

    @Override
    public ResponseJson existFriendsByUserId(String userId, String fuserId) {

        // 获取用户
        User user = userMapper.getByUserId(fuserId);

        // 获取群
        Group byGroupId = groupMapper.getByGroupId(fuserId);

        if(user == null && byGroupId == null){
            return new ResponseJson().setData("msg","不存在该用户或群");
        }

        // 看是否已是好友
        List<User> friendsByUserId = userMapper.getFriendsByUserId(userId);

        for (User list:friendsByUserId){
            if(list.getUserId() .equals(fuserId)){
                return new ResponseJson().setData("msg","已经是好友");
            }
        }

        // 看是否已是群成员
        List<String> groupIdsByUserId = userMapper.getGroupIdsByUserId(userId);
        for (String list:groupIdsByUserId){
            if(list.equals(fuserId)){
                return new ResponseJson()
                        .setData("msg","已经是群成员");
            }
        }

        if(user == null ){
            return new ResponseJson().setData("group",byGroupId);
        } else {
            return new ResponseJson().setData("user",user);
        }
    }

    /**
     *  加群及加好友处理
     * @param userId
     * @param fId
     * @param tp
     * @return
     */
    @Override
    public ResponseJson addfIdById(String userId, String fId, String tp) throws Exception {
        String msg = null;
        try {
            if(tp.equals("1")){
                userMapper.addFriendById(userId,fId);
                msg = "已添加";
            } else {
                groupMapper.addMemberById(userId,fId);
                msg = "已加入";
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception();
        }
        return new ResponseJson().success()
                .setData("msg",msg)
                .setData("warn","回到聊天页面点击刷新即可显示");
    }

    @Override
    public ResponseJson updateAvatar(String userId, String fileUrl) throws Exception {
        try {
            userMapper.updateAvatarById(userId, fileUrl);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception();
        }
        return new ResponseJson().success()
                .setData("msg","头像更换成功");
    }

    @Override
    public ResponseJson deleteById(String userId, String fId, String tp) throws Exception {
        String msg;
        try {
            if(tp.equals("1")){
                userMapper.deleteFriendById(userId,fId);
                msg = "删除成功";
            } else {
                groupMapper.deleteMemberById(userId,fId);
                msg = "退群成功";
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception();
        }

        return new ResponseJson().success()
                .setData("msg",msg);
    }

}
