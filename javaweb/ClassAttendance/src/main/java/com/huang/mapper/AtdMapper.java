package com.huang.mapper;

import com.huang.model.entity.SignInfo;
import com.huang.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@Mapper
public interface AtdMapper {

    List<SignInfo> queryByTeacherId(@PathParam("teacherId") String teacherId);

    int insertSign(Map<String, Object> map);

    int insertRecordSign(@PathParam("studentId") String studentId, @PathParam("signId") int signId);

    List<SignInfo> queryByStudentId(@PathParam("studentId") String teacherId);

    List<User> queryStusBySignId(@PathParam("signId") int signId);

    String queryIsSign(@PathParam("signId") int signId, @PathParam("studentId") String studentId);

    SignInfo queryInfoBySignId(@PathParam("signId") int signId);

}
