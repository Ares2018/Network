package com.core.network.option;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.core.network.api.ApiTask;
import com.core.network.callback.AgentCallback;

import okhttp3.Response;

/**
 * 解析响应体 - 接口
 *
 * @author a_liYa
 * @date 2017/12/27 20:59.
 */
public interface ParseResponse {

    // 子线程
    @WorkerThread
    <T> void onParseResponse(@NonNull Response response, @NonNull AgentCallback<T> callback,
                             @NonNull ApiTask apiTask);

}
