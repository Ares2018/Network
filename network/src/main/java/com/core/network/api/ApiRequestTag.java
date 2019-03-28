package com.core.network.api;

import okhttp3.Request;

/**
 * 对 {@link Request#tag} 的封装
 *
 * @author a_liYa
 * @date 2019/3/28 10:44.
 */
public final class ApiRequestTag {

    public ApiRequestTag(Request request, Object tag) {
        this.mRequest = request;
        this.mTag = tag;
    }

    private Object mTag;

    private Request mRequest;

    public Object getTag() {
        return mTag;
    }

    public void setTag(Object tag) {
        this.mTag = tag;
    }

    public Request getRequest() {
        return mRequest;
    }

    public void setRequest(Request request) {
        this.mRequest = request;
    }

}
