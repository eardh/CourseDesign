package com.huang.mapper;

import com.huang.model.entity.Course;
import com.huang.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

import javax.websocket.server.PathParam;
import java.util.List;

@Mapper
public interface UserMapper {

    int insertUser(User user);

    User queryByUserId(@PathParam("userId") String userId);

    User queryByOpenId(@PathParam("openId") String openId);

    int updateByOpenId(@PathParam("openId") String openId,
                       @PathParam("userId") String userId,
                       @PathParam("userName") String userName,
                       @PathParam("identity") int identity);

    List<Course> queryCourseByStudentId(@PathParam("studentId") String studentId);

    List<Course> queryCoursesByTeacher(@PathParam("teacherId") String teacherId);

    String queryEducation(@PathParam("userId") String userId,
                          @PathParam("userName") String userName,
                          @PathParam("identity") int identity);

}
