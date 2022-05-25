package com.dahuang.controller;

import com.dahuang.model.enums.ProductTypes;
import com.dahuang.model.response.ResponseModel;
import com.dahuang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dahuang
 * @date 2021/6/10 17:10
 */
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/product/page", method = RequestMethod.POST)
    public ResponseModel updateProductInfo(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "type",required = false) String type,
            @RequestParam(value = "productName",required = false) String productName) {
        return productService.pageProducts(currentPage,
                pageSize,
                type != null ? !type.isEmpty()  ? ProductTypes.valueOf(type) : null : null,
                productName != null ? !productName.isEmpty()  ? productName : null : null);
    }

    @RequestMapping(value = "/product/details", method = RequestMethod.POST)
    public ResponseModel queryProductInfo(
            @RequestParam("productID") String productID) {
        return productService.queryProductInfo(productID);
    }
}
