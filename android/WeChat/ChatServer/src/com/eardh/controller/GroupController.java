package com.eardh.controller;

import com.eardh.annotation.EarController;
import com.eardh.annotation.EarMapping;
import com.eardh.annotation.EarParam;
import com.eardh.model.vojo.EarResponse;
import com.eardh.service.GroupService;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

@EarController
@EarMapping("/group")
public class GroupController {

    private GroupService groupService = new GroupService();

    @EarMapping("/add")
    public EarResponse addMember(@EarParam("userId") String userId,
                                 @EarParam("groupId") String groupId) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return groupService.addMember(userId, groupId);
    }
}
