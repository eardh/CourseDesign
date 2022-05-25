package com.dahuang.config.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.dahuang.model.enums.ResponseCode;
import com.dahuang.model.response.ResponseModel;
import com.dahuang.until.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 拦截器
 * @author dahuang
 * @date 2021/6/9 14:39
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截预处理
     * @author dahuang
     * @date 2021/6/9 14:40
     * @param request
     * @param response
     * @param handler
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {

        logger.info("拦截预处理");
        HttpSession session = request.getSession();
        Object userToken = session.getAttribute(Constant.USER_TOKEN);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        if (userToken == null) {
            logger.info("用户未登录");
            response.getWriter().print(JSONObject.toJSON(ResponseModel.fail(ResponseCode.USER_NOT_LOGGED_IN)));
            return false;
        }

        Map<String,Object>  token = (Map<String, Object>) userToken;
        String uri = request.getRequestURI();

        if(!Constant.Permission.get(token.get("role")).contains(uri)) {
            logger.info("用户无权限");
            response.getWriter().print(JSONObject.toJSON(ResponseModel.fail(ResponseCode.NOT_PERMISSIONS)));
            return false;
        }

        logger.info("访问成功");
        return true;
    }
}
