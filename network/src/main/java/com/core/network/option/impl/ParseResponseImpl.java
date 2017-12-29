package com.core.network.option.impl;

import android.support.annotation.NonNull;

import com.core.network.ApiManager;
import com.core.network.api.ApiTask;
import com.core.network.callback.AgentCallback;
import com.core.network.option.ParseResponse;
import com.core.network.utils.GenericUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * {@link ParseResponse}的默认实现
 *
 * @author a_liYa
 * @date 2017/12/28 09:47.
 */
public class ParseResponseImpl implements ParseResponse {

    @Override
    public <T> void onParseResponse(@NonNull Response response, @NonNull AgentCallback<T>
            callback, @NonNull ApiTask apiTask) {
        if (200 == response.code()) { // 请求成功
            try {
                handleBody(response, callback, apiTask.getClass());
            } catch (IOException e) {
                ApiManager.getApiConfig()
                        .getExceptionTransform().onExceptionTransform(e, callback);
            }
        } else {
            callback.onError(response.code(), "HTTP status code != 200");
        }
    }

    private <T> void handleBody(@NonNull Response response, @NonNull AgentCallback<T> callback,
                                @NonNull Class<? extends ApiTask> clazz) throws IOException {
        String body = response.body().string();

        T data;
        Type type = GenericUtils.getGenericType(clazz);
        if (type == null) {
            throw new IllegalArgumentException(getClass().getName() + "泛型未声明或者不合法");
        } else if (type == Void.class) {
            data = null;
        } else if (type == String.class) {
            data = (T) body;
        } else {
            data = ApiManager.getApiConfig().getJsonParse().onJsonParse(body, type);
        }
        callback.onSuccess(data);
    }

}
