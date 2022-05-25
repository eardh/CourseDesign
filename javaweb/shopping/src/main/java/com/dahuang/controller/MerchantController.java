package com.dahuang.controller;

import com.dahuang.mapper.UserMapper;
import com.dahuang.model.response.ResponseModel;
import com.dahuang.service.ProductService;
import com.dahuang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dahuang
 * @date 2021/6/9 16:42
 */
@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/product/add", method = RequestMethod.POST)
    public ResponseModel insertProduct(
            @RequestParam(value = "image",required = false) MultipartFile file,
            HttpServletRequest request) {
        return productService.insertProduct(file,request);
    }

    @RequestMapping(value = "/product/alter", method = RequestMethod.POST)
    public ResponseModel updateProductInfo(
            @RequestParam(value = "image",required = false) MultipartFile file,
            HttpServletRequest request) {
        return productService.updateProductInfo(file,request);
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseModel pageProductByMerchant(
            @RequestParam("merchantID") String merchantID,
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("pageSize") Integer pageSize) {
        return productService.queryByMerchantID(currentPage,pageSize,merchantID);
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public ResponseModel pageOrderByMerchant(
            @RequestParam("merchantID") String merchantID,
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("pageSize") Integer pageSize) {
        return userService.MerchantIDToOrders(currentPage,pageSize,merchantID);
    }

    @RequestMapping(value = "/product/delete", method = RequestMethod.POST)
    public ResponseModel deleteProductByID(
            @RequestParam("ID") String ID) {
        return productService.deleteProduct(ID);
    }

    @RequestMapping(value = "/product/send", method = RequestMethod.POST)
    public ResponseModel productSend(
            @RequestParam("ID") String ID) {
        return userService.updateOrderSend(ID);
    }

}
