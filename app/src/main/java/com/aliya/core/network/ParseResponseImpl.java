package com.aliya.core.network;

import android.support.annotation.NonNull;

import com.core.network.api.ApiTask;
import com.core.network.callback.AgentCallback;
import com.core.network.module.ParseResponse;
import com.core.network.utils.GenericUtils;
import com.core.network.ApiManager;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;

/**
 * ParseResponse的示例
 *
 * @author a_liYa
 * @date 2017/12/28 09:47.
 */
public class ParseResponseImpl implements ParseResponse {

    @Override
    public <T> void onParseResponse(@NonNull Response response, @NonNull AgentCallback<T>
            callback, @NonNull Class<? extends ApiTask> clazz) {
        if (200 == response.code()) { // 请求成功
            try {
                handleBody(response, callback, clazz);
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


        List<Class> generics = GenericUtils.getGenericClass(clazz);
        T data = null;
        if (generics.size() == 0) {
            throw new IllegalArgumentException(getClass().getName() + "泛型声明不合法");
        } else if (generics.size() == 1) { // 只有一个泛型
            Class indexClazz = generics.get(0);
            if (indexClazz == Void.class) { // Void.class
                data = null;
            } else if (indexClazz == String.class) {
                data = (T) body;
            }
        }
        callback.onSuccess(data);
    }

}
