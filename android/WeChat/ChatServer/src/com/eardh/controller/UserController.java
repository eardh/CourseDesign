package  com.eardh.controller;

import com.eardh.annotation.EarController;
import com.eardh.annotation.EarMapping;
import com.eardh.annotation.EarParam;
import com.eardh.model.pojo.Message;
import com.eardh.model.vojo.EarResponse;
import com.eardh.service.GroupService;
import com.eardh.service.UserService;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

@EarController
@EarMapping("/user")
public class UserController {

    private UserService userService = new UserService();
    private GroupService groupService = new GroupService();

    @EarMapping("/login")
    public EarResponse login(@EarParam("userId") String userId,
                      @EarParam("password") String password,
                      @EarParam("ip") String ip) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return userService.login(userId, password, ip);
    }

    @EarMapping("/logout")
    public EarResponse logout(@EarParam("ip") String ip,
                              @EarParam("id") String id) throws SQLException {
        return userService.logout(ip, id);
    }

    @EarMapping("/friends")
    public EarResponse friends(@EarParam("userId") String userId) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return userService.getFriends(userId);
    }

    @EarMapping("/groups")
    public EarResponse groups(@EarParam("userId") String userId) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return userService.getGroups(userId);
    }

    @EarMapping("/chat/friend")
    public EarResponse privateChat(@EarParam("message") Message message) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return userService.chatFriend(message);
    }

    @EarMapping("/chat/group")
    public EarResponse groupChat(@EarParam("message") Message message) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return groupService.chatGroup(message);
    }

    @EarMapping("/add")
    public EarResponse addPerson(@EarParam("userId") String userId,
                                 @EarParam("fuserId") String fuserId) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return userService.addFriend(userId, fuserId);
    }

    @EarMapping("/register")
    public EarResponse registerUser(@EarParam("nickname") String nickname,
                                    @EarParam("password") String password) throws SQLException {
        return userService.registerUser(nickname, password);
    }
}
