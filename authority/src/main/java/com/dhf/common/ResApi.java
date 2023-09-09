package com.dhf.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;

import static com.dhf.common.ResCode.SUCCESS;
import static com.dhf.common.ResCode.ERROR;
import static com.dhf.common.ResCode.NO_AUTH;
import static com.dhf.common.ResCode.NO_LOGIN;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/09 11:20
 * 返回结果集
 */
@Data
@Accessors(chain = true)
public class ResApi {
    private boolean isSuccess;
    private int code;
    private String msg;
    private HashMap<String,Object> data;
    ResApi(){
        data = new HashMap<String,Object>();
    }
    public static ResApi success(){
        ResApi resApi = new ResApi();
        resApi.setSuccess(true);
        resApi.setCode(SUCCESS.code());
        resApi.setMsg(SUCCESS.getMsg());
        return resApi;
    }
    public static ResApi success(HashMap<String,Object> map){
        ResApi success = success();
        success.getData().putAll(map);
        return success;
    }
    public static ResApi success(String key,Object value){
        ResApi success = success();
        success.getData().put(key,value);
        return success;
    }
    public static ResApi error(){
        ResApi resApi = new ResApi();
        resApi.setSuccess(false);
        resApi.setCode(ERROR.code());
        resApi.setMsg(ERROR.getMsg());
        return resApi;
    }
    public static ResApi error(HashMap<String,Object> map){
        ResApi resApi = error();
        resApi.getData().putAll(map);
        return resApi;
    }
    public static ResApi error(String key,Object value){
        ResApi resApi = error();
        resApi.getData().put(key,value);
        return resApi;
    }

}
