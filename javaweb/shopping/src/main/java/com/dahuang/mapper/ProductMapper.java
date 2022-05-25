package com.dahuang.mapper;

import com.dahuang.model.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * @author dahuang
 * @date 2021/6/8 16:50
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product>{

    List<Product> pageQueryByType(Map<String,Object> map);

    List<Product> QueryByMerchantID(Map<String,Object> map);

}
