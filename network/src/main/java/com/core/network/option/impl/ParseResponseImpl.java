package com.core.network.option.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.core.network.ApiManager;
import com.core.network.api.ApiGenericCarrier;
import com.core.network.api.ApiTask;
import com.core.network.callback.AgentCallback;
import com.core.network.callback.ApiCallback;
import com.core.network.option.ParseResponse;
import com.core.network.utils.Generics;

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
                handleBody(response, callback, apiTask);
            } catch (IOException e) {
                ApiManager.getApiConfig()
                        .getExceptionTransform().onExceptionTransform(e, callback);
            }
        } else {
            callback.onError(response.code(), "HTTP status code != 200");
        }
    }

    private <T> void handleBody(@NonNull Response response, @NonNull AgentCallback<T> callback,
                                @NonNull ApiTask apiTask) throws IOException {
        String body = response.body().string();

        T data = null;
        ApiCallback<T> apiCallback = apiTask.getCallback();
        if (apiCallback != null) {
            Type typeOf = Generics.getGenericType(apiCallback.getClass(), ApiCallback.class);
            if (typeOf == null) { // 通过接口 ApiGenericType 获取泛型携带者
                if (apiCallback instanceof ApiGenericCarrier) {
                    ApiGenericCarrier carrier = ((ApiGenericCarrier) apiCallback);
                    if (carrier != null) {
                        typeOf = Generics.getGenericType(carrier.getGenericRealize(), carrier.getGenericDefine());
                    }
                }
            }

            if (typeOf == null) {
                data = null; // 泛型未声明或者不合法
                if (ApiManager.isDebuggable()) {
                    Log.e(ApiManager.LOG_TAG, apiCallback.getClass().getName() + "类型参数未声明");
                }
            } else if (typeOf == Void.class) {
                data = null;
            } else if (typeOf == String.class) {
                data = (T) body;
            } else {
                data = ApiManager.getApiConfig().getJsonParse().onJsonParse(body, typeOf);
            }
        }
        callback.onSuccess(data);
    }

}
