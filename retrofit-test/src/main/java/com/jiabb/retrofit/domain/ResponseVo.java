package com.jiabb.retrofit.domain;

import com.alibaba.fastjson.JSON;

/**
 * @author jiabinbin
 * @date 2020/12/3 8:48 上午
 * @classname ResponseVo
 * @description 反参实体类
 */
public class ResponseVo<T> {

    private String message;
    private Integer status;
    private T data;

    public ResponseVo(){}

    public ResponseVo(String message, Integer status) {
        this.message = message;
        this.status = status;
    }

    public ResponseVo(String message, Integer status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public static ResponseVo ofSusses(){
        return new ResponseVo("susses",200);
    }


    public static ResponseVo ofError(){
        return new ResponseVo("error",500);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
