package com.huang.model.response;

import com.huang.model.enums.ResponseCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应类
 * @author dahuang
 * @date 2021/6/8 9:21
 */
@Data
public class ResponseModel implements Serializable {

    private static final long serialVersionUID = -4505655308965878999L;

    private Integer code;

    private String message;

    private Object data;

    private ResponseModel() {
    }

    public ResponseModel(ResponseCode responseCode, Object data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMsg();
        this.data = data;
    }

    /**
     * 设置状态码
     * @author dahuang
     * @date 2021/6/8 9:17
     * @param responseCode
     */
    private void setResponseCode(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMsg();
    }

    /**
     * 成功
     * @author dahuang
     * @date 2021/6/8 9:15
     * @return com.dahuang.model.response.ResponseModel
     */
    public static ResponseModel success() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setResponseCode(ResponseCode.SUCCESS);
        return responseModel;
    }

    /**
     * 成功
     * @author dahuang
     * @date 2021/6/8 9:14
     * @param data
     * @return com.dahuang.model.response.ResponseModel
     */
    public static ResponseModel success(Object data) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setResponseCode(ResponseCode.SUCCESS);
        responseModel.setData(data);
        return responseModel;
    }

    /**
     * 失败
     * @author dahuang
     * @date 2021/6/8 9:10
     * @param code
     * @param message
     * @return com.dahuang.model.response.ResponseModel
     */
    public static ResponseModel fail(Integer code, String message) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setCode(code);
        responseModel.setMessage(message);
        return responseModel;
    }

    /**
     * 失败
     * @param responseCode
     * @return
     */
    public static ResponseModel fail(ResponseCode responseCode) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setResponseCode(responseCode);
        return responseModel;
    }

    public ResponseModel setMessage(String message) {
        this.message = message;
        return this;
    }
}
