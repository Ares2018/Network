package com.core.network.okhttp;

import com.core.network.api.ApiRequestTag;
import com.core.network.callback.ApiProgressCallback;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ProgressInterceptor 进度拦截器
 *
 * @author a_liYa
 * @date 16/8/6 19:46.
 */
public class ProgressInterceptor implements Interceptor {

    public ProgressInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 拦截Request
        Request request = chain.request();
        Object tag = request.tag();
        if (tag instanceof ApiRequestTag) {
            tag = ((ApiRequestTag) tag).getTag();
            if (tag instanceof ApiProgressCallback) {
                ApiProgressCallback progressCallback = (ApiProgressCallback) tag;
                if (request.body() != null) {
                    // 包装请求体
                    request = request.newBuilder()
                            .post(new ProgressRequestBody(request.body(), progressCallback))
                            .build();
                }

                // 拦截Response
                Response response = chain.proceed(request);

                if (response.body() != null) {
                    // 包装响应体并返回
                    response = response.newBuilder()
                            .body(new ProgressResponseBody(response.body(), progressCallback))
                            .build();
                }
                return response;
            }
        }
        return chain.proceed(request);
    }
}
