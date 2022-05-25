package com.dahuang.service.Impl;

import com.dahuang.mapper.ProductMapper;
import com.dahuang.model.entity.Product;
import com.dahuang.model.enums.ProductTypes;
import com.dahuang.model.response.ResponseModel;
import com.dahuang.service.FileUploadService;
import com.dahuang.service.ProductService;
import com.dahuang.until.ParameterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dahuang
 * @date 2021/6/10 16:34
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private ProductMapper productMapper;

    @Value("${FileService.imagePath}")
    private String imagePath;

    @Override
    public ResponseModel insertProduct(MultipartFile file, HttpServletRequest request) {
        String imageURI = imagePath + "default.jpg";
        Product product = (Product) ParameterUtils.RequestParamToObj(Product.class,request);

        if ( file != null ? !file.isEmpty() : false ) {
            ResponseModel upload = fileUploadService.upload(file);
            if( upload.getCode() == -1 ) {
                return upload.setMessage("商品添加失败");
            }
            Object data = upload.getData();
            imageURI = String.valueOf(data);
        }

        product.setImage(imageURI);
        productMapper.insertModel(product);
        return ResponseModel.success().setMessage("商品添加成功");
    }

    @Override
    public ResponseModel updateProductInfo(MultipartFile file, HttpServletRequest request) {

        Product product = (Product) ParameterUtils.RequestParamToObj(Product.class,request);
        Product product1 = productMapper.queryById(product.getID());
        String imageURI = product1.getImage();

        if ( file != null ? !file.isEmpty() : false ) {
            ResponseModel upload = fileUploadService.upload(file);
            if( upload.getCode() == -1 ) {
                return upload.setMessage("商品修改失败");
            }

            Object data = upload.getData();
            imageURI = String.valueOf(data);
        }
        product.setImage(imageURI);
        logger.info(String.valueOf(product));

        product1.setImage(product.getImage());
        product1.setType(product.getType());
        product1.setProductName(product.getProductName());
        product1.setPrice(product.getPrice());
        product1.setStock(product.getStock());
        product1.setDescription(product.getDescription());
        logger.info(String.valueOf(product1));

        productMapper.updateModel(product1);

        return ResponseModel.success().setMessage("商品修改成功");
    }

    @Override
    public ResponseModel pageProducts(int currentPage, int pageSize, ProductTypes type,String productName) {

        int startIndex = (currentPage-1) * pageSize;
        Map<String,Object> map = new HashMap<>();

        map.put("startIndex",startIndex);
        map.put("pageSize",pageSize);
        map.put("type",type);
        map.put("productName",productName);

        List<Product> products = productMapper.pageQueryByType(map);

        return ResponseModel.success(products);

    }

    @Override
    public ResponseModel deleteProduct(String ID) {

        productMapper.deleteByID(ID);

        return ResponseModel.success().setMessage("商品清除成功");

    }

    @Override
    public ResponseModel queryProductInfo(String ID) {
        Product product = productMapper.queryById(ID);
        return ResponseModel.success(product);
    }

    @Override
    public ResponseModel queryByMerchantID(int currentPage, int pageSize, String merchantID) {
        int startIndex = (currentPage-1) * pageSize;
        Map<String,Object> map = new HashMap<>();

        map.put("startIndex",startIndex);
        map.put("pageSize",pageSize);
        map.put("merchantID",merchantID);

        List<Product> products = productMapper.QueryByMerchantID(map);

        return ResponseModel.success(products);
    }

}
