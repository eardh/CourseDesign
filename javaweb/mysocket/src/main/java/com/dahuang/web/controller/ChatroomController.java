package com.dahuang.web.controller;


import com.dahuang.model.json.ResponseJson;
import com.dahuang.service.UserService;
import com.dahuang.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/chatroom")
public class ChatroomController {

    @Autowired
    UserService userService;

    /**
     * 描述：登录成功后，调用此接口进行页面跳转
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String toChatroom(HttpSession session) {
        if (session.getAttribute(Constant.USER_TOKEN) == null) {
            return "redirect:/";
        }
        return "/views/chatroom";
    }

    /**
     * 描述：登录成功跳转页面后，调用此接口获取用户信息
     * @param
     * @return
     */
    @RequestMapping(value = "/get_User", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson getUser(HttpSession session) {
        Object userId = session.getAttribute(Constant.USER_TOKEN);
        return userService.getByUserId((String)userId);
    }

    /**
     * 添加用户或群时查询 是否存在等
     * @param
     * @return
     */
    @RequestMapping(value = "/add_friend", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson existaddFriend(@RequestParam String userId , @RequestParam String fuserId) {

        return userService.existFriendsByUserId(userId,fuserId);

    }

    /**
     *  添加群或好友
     * @param userId
     * @param fId
     * @param tp
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toadd_friend", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson toaddFriend(
            @RequestParam String userId ,
            @RequestParam String fId,
            @RequestParam String tp) throws Exception {

        return userService.addfIdById(userId,fId,tp);

    }

    /**
     *  换头像
     * @param userId
     * @param filename
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update_avatar", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson updateAvatar(
            @RequestParam String userId ,
            @RequestParam String filename) throws Exception {

        return userService.updateAvatar(userId, "img/avatar/"+filename);
    }

    /**
     *  删除好友或群
     * @param userId
     * @param fId
     * @param tp
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteFri_Grp", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson deleteFri_Grp(
            @RequestParam String userId ,
            @RequestParam String fId,
            @RequestParam String tp) throws Exception {

        return userService.deleteById(userId,fId,tp);

    }

}
