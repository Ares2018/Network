package com.aliya.core.network.module;

import com.aliya.core.network.api.ApiTask;
import com.aliya.core.network.callback.AgentCallback;

import okhttp3.Response;

/**
 * 解析响应体 - 接口
 *
 * @author a_liYa
 * @date 2017/12/27 20:59.
 */
public interface ParseResponse {

    // 子线程
    <T> void onParseResponse(Response response, AgentCallback<T> callback,
                             Class<? extends ApiTask> clazz);

}
