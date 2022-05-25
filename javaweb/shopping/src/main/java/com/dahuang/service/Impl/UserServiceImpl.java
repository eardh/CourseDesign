package com.dahuang.service.Impl;


import com.alibaba.fastjson.JSON;
import com.dahuang.mapper.OrderMapper;
import com.dahuang.mapper.ProductMapper;
import com.dahuang.mapper.ShopCartMapper;
import com.dahuang.mapper.UserMapper;
import com.dahuang.model.entity.Order;
import com.dahuang.model.entity.Product;
import com.dahuang.model.entity.ShopCart;
import com.dahuang.model.entity.User;
import com.dahuang.model.enums.ResponseCode;
import com.dahuang.model.enums.Roles;
import com.dahuang.model.response.ResponseModel;
import com.dahuang.service.FileUploadService;
import com.dahuang.service.UserService;
import com.dahuang.until.Constant;
import com.dahuang.until.ParameterUtils;
import com.dahuang.until.UuidUtils;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dahuang
 * @date 2021/6/8 11:19
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileUploadService fileUploadService;

    @Value("${FileService.imagePath}")
    private String imagePath;

    @Autowired
    private ShopCartMapper shopCartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StringEncryptor encryptor;

    @Override
    public ResponseModel login(String userName, String password, HttpSession session) {

        User user = userMapper.queryByName(userName);

        if (user == null ) {
            return ResponseModel.fail(ResponseCode.FAILURE).setMessage("用户名不存在");
        }

        if ( !encryptor.decrypt(user.getPassword()).equals(password) ){
            return ResponseModel.fail(ResponseCode.FAILURE).setMessage("密码错误");
        }

        if (!user.isStatus()) {
            return ResponseModel.fail(ResponseCode.NOT_APPLICATION_CONFIRM).setMessage("您的相关申请还在审核中···");
        }
        Map<String,Object> Token = new HashMap<>();

        Token.put("role",user.getRole());
        Token.put("userID",user.getID());
        Token.put("uuID",UuidUtils.getRandomUUID());
        Token.put("userName",user.getUserName());

        session.setAttribute(Constant.USER_TOKEN, Token);

        Map<String,Object> map = new HashMap<>();
        map.put("userID",user.getID());
        map.put("userRole",user.getRole());

        return ResponseModel.success(map).setMessage("登录成功");

    }

    @Override
    public ResponseModel logout(HttpSession session) {
        Object userToken = session.getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            return ResponseModel.fail(ResponseCode.USER_NOT_LOGGED_IN);
        }
        session.removeAttribute(Constant.USER_TOKEN);
        return ResponseModel.success().setMessage("注销成功");
    }

    @Override
    public ResponseModel updateUserInfo(MultipartFile file, HttpServletRequest request) {

        User user = (User) ParameterUtils.RequestParamToObj(User.class,request);
        String imageURI = user.getAvatarImage();

        if ( file != null ? !file.isEmpty() : false ) {
            ResponseModel upload = fileUploadService.upload(file);
            if( upload.getCode() == -1 ) {
                return upload.setMessage("修改失败");
            }

            Object data = upload.getData();
            imageURI = String.valueOf(data);

        }

        user.setAvatarImage(imageURI);

        userMapper.updateModel(user);

        return ResponseModel.success().setMessage("修改成功");
    }

    @Override
    public ResponseModel checkUserName(String userName) {

        User user = userMapper.queryByName(userName);
        if (user != null) {
            return ResponseModel.fail(ResponseCode.FAILURE).setMessage("用户名已存在");
        }
        return ResponseModel.success().setMessage("用户名可用");

    }

    @Override
    public ResponseModel addToShopCart(HttpServletRequest request) {

        ShopCart cart = (ShopCart) ParameterUtils.RequestParamToObj(ShopCart.class, request);

        Map<String,Object> map = new HashMap<>();
        map.put("customerID",cart.getCustomerID());
        map.put("merchantID",cart.getMerchantID());
        map.put("productID",cart.getProductID());
        ShopCart shopCart = shopCartMapper.queryByTwoID(map);
        if (shopCart == null) {
            shopCartMapper.insertModel(cart);
            return ResponseModel.success().setMessage("加入购物车成功");
        }
        cart.setID(shopCart.getID());
        cart.setProductCount(cart.getProductCount() +  shopCart.getProductCount());
        shopCartMapper.updateModel(cart);
        return ResponseModel.success().setMessage("商品数目增加成功");
    }

    @Override
    public ResponseModel queryUserShopCart(String customerID) {

        List<Map<String,Object>> list = new ArrayList<>();
        List<ShopCart> shopCarts = shopCartMapper.queryByUserID(customerID);
        logger.info(String.valueOf(shopCarts));
        if (!shopCarts.isEmpty()) {
            shopCarts.stream().forEach( k -> {
                Map<String,Object> map = new HashMap<>();
                map.put("ID",k.getID());
                map.put("customerID",k.getCustomerID());
                map.put("merchantID",k.getMerchantID());
                map.put("productID",k.getProductID());
                map.put("productCount",k.getProductCount());
                Product product = productMapper.queryById(k.getProductID());
                map.put("productName", product.getProductName());
                map.put("description",product.getDescription());
                map.put("image", product.getImage());
                map.put("stock", product.getStock());
                map.put("price", product.getPrice());
                list.add(map);
            });
            return ResponseModel.success(JSON.toJSON(list));
        }
        return ResponseModel.fail(ResponseCode.FAILURE).setMessage("您的购物车为空");
    }

    @Override
    public ResponseModel deleteCart(String ID) {
        shopCartMapper.deleteByID(ID);
        return ResponseModel.success().setMessage("删除成功");
    }

    /**
     * 上锁，防止库存溢出
     * @author dahuang
     * @date 2021/6/15 16:23
     * @param request
     * @return com.dahuang.model.response.ResponseModel
     */
    @Override
    synchronized public ResponseModel clearCart(HttpServletRequest request) {

        Order order = (Order) ParameterUtils.RequestParamToObj(Order.class, request);

        logger.info(String.valueOf(order));

        Product product = productMapper.queryById(order.getProductID());

        if (product.getStock() < order.getCount()){
           return ResponseModel.fail(ResponseCode.FAILURE).setMessage("商品库存不足");
        }

        product.setStock((product.getStock() - order.getCount()));
        logger.info("生成账单中...");

        String uuid = UuidUtils.getRandomUUID();
        String mark = uuid + System.currentTimeMillis();
        order.setSerial_number(mark);
        logger.info("订单编号为：" + mark);
        orderMapper.insertModel(order);
        productMapper.updateModel(product);
        shopCartMapper.deleteByID(request.getParameter("cartID"));
        return ResponseModel.success(mark).setMessage("下单成功");
    }

    @Override
    public ResponseModel queryUserOrder(int currentPage, int pageSize,String userID) {
        Map<String,Object> map = new HashMap<>();
        Integer startIndex = ( currentPage-1 ) * pageSize;
        map.put("startIndex",startIndex);
        map.put("pageSize",pageSize);
        map.put("customerID",userID);
        List<Order> orders = orderMapper.queryByCustomerID(map);
        return ResponseModel.success(orders);
    }

    @Override
    public ResponseModel updateOrderStatus(String type, String orderID) {
        Order order = orderMapper.queryById(orderID);
        if (type.equals("received")) {
            order.setReceived(true);
            orderMapper.updateModel(order);
            return ResponseModel.success().setMessage("收货成功");
        }
        order.setConfirm(true);
        orderMapper.updateModel(order);
        return ResponseModel.success().setMessage("订单确认成功");
    }

    @Override
    public ResponseModel MerchantIDToOrders(int currentPage, int pageSize, String merchantID) {

        Map<String,Object> map = new HashMap<>();
        Integer startIndex = ( currentPage-1 ) * pageSize;
        map.put("startIndex",startIndex);
        map.put("pageSize",pageSize);
        map.put("merchantID",merchantID);
        List<Order> orders = orderMapper.queryByMerchantID(map);
        return ResponseModel.success(orders);
    }

    @Override
    public ResponseModel updateOrderSend(String ID) {
        Order order = orderMapper.queryById(ID);
        order.setSend(true);
        orderMapper.updateModel(order);
        return ResponseModel.success().setMessage("商品发货成功");
    }

    @Override
    public ResponseModel pageUsersByManager(int currentPage, int pageSize) {
        Map<String,Object> map = new HashMap<>();
        Integer startIndex = ( currentPage - 1 ) * pageSize;
        map.put("startIndex",startIndex);
        map.put("pageSize",pageSize);
        map.put("status",false);
        List<User> users = userMapper.queryApplicationUsersToPage(map);
        return ResponseModel.success(users);
    }

    @Override
    public ResponseModel agreeMerchantApplication(String ID) {
        User user = userMapper.queryById(ID);
        user.setStatus(true);
        userMapper.updateModel(user);
        return ResponseModel.success().setMessage("申请通过成功");
    }

    @Override
    public ResponseModel rejectMerchantApplication(String ID) {
        userMapper.deleteByID(ID);
        return ResponseModel.success().setMessage("申请拒绝成功");
    }

    @Override
    public ResponseModel register(MultipartFile file, HttpServletRequest request) {

        String imageURI = imagePath + "default.jpg";
        User user = (User) ParameterUtils.RequestParamToObj(User.class,request);

        if ( file != null ? !file.isEmpty() : false) {
            ResponseModel upload = fileUploadService.upload(file);
            if( upload.getCode() == -1 ) {
                return upload.setMessage("注册失败");
            }

            Object data = upload.getData();
            imageURI = String.valueOf(data);

        }

        user.setAvatarImage(imageURI);

        user.setPassword(encryptor.encrypt(user.getPassword()));

        if (user.getRole().equals(Roles.Customer)) {
            user.setStatus(true);
            userMapper.insertModel(user);
            return ResponseModel.success().setMessage("注册成功");
        }
        user.setStatus(false);
        userMapper.insertModel(user);
        return ResponseModel.success().setMessage("商家入驻申请成功，正在等待审核");

    }

}
