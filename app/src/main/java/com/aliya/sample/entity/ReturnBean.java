package com.aliya.sample.entity;

import java.io.Serializable;

/**
 * 数据返回的最外层包装
 *
 * @author a_liYa
 * @date 16/10/18 19:54.
 */
public class ReturnBean<T> implements Serializable {

    private int code;

    private String message;

    public String request_id;

    private T data;

    public ReturnBean() {
    }

    public ReturnBean(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
