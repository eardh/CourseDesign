package com.dahuang.mapper;

import com.dahuang.model.entity.ShopCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author dahuang
 * @date 2021/6/8 19:31
 */
@Mapper
public interface ShopCartMapper extends BaseMapper<ShopCart>{

    ShopCart queryByTwoID(Map<String,Object> map);

    List<ShopCart> queryByUserID(@Param("customerID") String userID);
}
