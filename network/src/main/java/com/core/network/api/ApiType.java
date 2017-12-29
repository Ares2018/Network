package com.core.network.api;

/**
 * 网络请求类型 - 枚举
 *
 * @author a_liYa
 * @date 2017/12/26 21:36.
 */
public enum ApiType {

    GET("GET"),
    POST("POST"),
    POST_UPLOAD("POST_UPLOAD"),
    DOWNLOAD("DOWNLOAD");

    private String type;

    ApiType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
