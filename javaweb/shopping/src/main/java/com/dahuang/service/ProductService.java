package com.dahuang.service;

import com.dahuang.model.enums.ProductTypes;
import com.dahuang.model.response.ResponseModel;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dahuang
 * @date 2021/6/10 16:32
 */
@Transactional
public interface ProductService {

    /**
     * 增加商品
     * @author dahuang
     * @date 2021/6/10 16:35
     * @param file
     * @param request
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel insertProduct(MultipartFile file, HttpServletRequest request);

    /**
     * 修改商品
     * @author dahuang
     * @date 2021/6/10 16:40
     * @param file
     * @param request
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel updateProductInfo(MultipartFile file,HttpServletRequest request);

    /**
     * 商品进行分页处理
     * type为空时为对全部商品分页
     * 需要搜索商品时则进行name模糊搜索
     * @author dahuang
     * @date 2021/6/10 16:59
     * @param currentPage
     * @param pageSize
     * @param type
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel pageProducts(int currentPage, int pageSize, ProductTypes type,String productName);

    /**
     * 删除产品
     * @author dahuang
     * @date 2021/6/12 21:56
     * @param ID
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel deleteProduct(String ID);

    /**
     * 查询商品信息
     * @author dahuang
     * @date 2021/6/14 19:02
     * @param ID
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel queryProductInfo(String ID);


    /**
     * 商家查询商品分页
     * @author dahuang
     * @date 2021/6/15 10:35
     * @param currentPage
     * @param pageSize
     * @param merchantID
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel queryByMerchantID(int currentPage, int pageSize, String merchantID);

}
