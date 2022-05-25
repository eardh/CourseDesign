package com.dahuang.mapper;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author dahuang
 * @date 2021/6/8 16:03
 */
public interface BaseMapper<T> {

    T queryByName(@PathParam("userName") String userName);

    T queryById(@PathParam("ID") String ID);

    int insertModel(T model);

    int deleteByID(@PathParam("ID") String ID);

    int updateModel(T model);

    List<T> queryAll();

}
