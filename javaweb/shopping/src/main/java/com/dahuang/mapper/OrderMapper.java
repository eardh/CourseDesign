package com.dahuang.mapper;

import com.dahuang.model.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * @author dahuang
 * @date 2021/6/13 21:47
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order>{

    Order queryBySerialNumber(@Param("serial_number") String serial_number);

    List<Order> queryByCustomerID(Map<String,Object> map);

    List<Order>  queryByMerchantID(Map<String,Object> map);

}
