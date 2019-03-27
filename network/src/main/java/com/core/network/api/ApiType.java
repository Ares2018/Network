package com.core.network.api;

/**
 * 网络请求类型 - 枚举
 *
 * @author a_liYa
 * @date 2017/12/26 21:36.
 */
public enum ApiType {

    GET(),          // GET
    POST(),         // POST Type json content
    POST_FORM(),    // POST Type form
    POST_UPLOAD(),  // POST Type form
    DOWNLOAD();     // 暂未实现，占位

}
