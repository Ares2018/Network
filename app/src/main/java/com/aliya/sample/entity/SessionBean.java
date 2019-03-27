package com.aliya.sample.entity;

import java.io.Serializable;

/**
 * Session 信息 - JavaBean
 *
 * @author a_liYa
 * @date 2017/9/6 13:08.
 */
public class SessionBean implements Serializable {

    private String id;          // session id
    private String account_id;  // account_id
    private String device_id;   // 设备号（客户端设备唯一标示的md5编码，可用于native js sdk作为设备唯一的标识码）
    private boolean anonymous;  // 是否匿名(游客) true:匿名，false:非匿名
    private boolean expired;    // 是否过期 true:过期,false:未过期

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

}
