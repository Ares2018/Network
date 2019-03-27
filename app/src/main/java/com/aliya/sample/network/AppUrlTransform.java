package com.aliya.sample.network;

import com.core.network.option.UrlTransform;

/**
 * 自定义 url transform
 *
 * @author a_liYa
 * @date 2017/12/29 11:31.
 */
public class AppUrlTransform implements UrlTransform {

    @Override
    public String onUrlTransform(String api) {
        if (api != null && api.startsWith("/")) {
            return getBaseUrl() + api;
        }
        return api;
    }

    private String getBaseUrl() {
        return "https://apibeta.8531.cn";
    }

}
