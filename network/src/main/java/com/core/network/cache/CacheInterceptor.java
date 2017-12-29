package com.core.network.cache;

import android.text.TextUtils;

import com.core.network.ApiManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * CacheInterceptor - 设置缓存
 *
 * @author a_liYa
 * @date 16/7/6 下午5:39.
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        String cachePolicy = request.header(CachePolicy.headerKey());
        if (TextUtils.isEmpty(cachePolicy)) {
            cachePolicy = CachePolicy.defaultValue();
        }

        int maxAge = ApiManager.CACHE_TIME;
        String headerMaxAge = request.header(CachePolicy.maxAgeKey());
        if (!TextUtils.isEmpty(headerMaxAge)) {
            try {
                int parseMaxAge = Integer.parseInt(headerMaxAge);
                if (parseMaxAge >= 0) {
                    maxAge = parseMaxAge;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // 1、永不取缓存； 2、有网不取缓存
        if (TextUtils.equals(cachePolicy, CachePolicy.NO_CACHE)
                || TextUtils.equals(cachePolicy, CachePolicy.CACHE_NO_NETWORK)
                && ApiManager.isAvailable()) {
            request = request.newBuilder()
                    .removeHeader(CachePolicy.headerKey())
                    .removeHeader(CachePolicy.maxAgeKey())
                    .cacheControl(CacheControl.FORCE_NETWORK).build();
        } else if (TextUtils.equals(cachePolicy, CachePolicy.CACHE_ONLY)
                || TextUtils.equals(cachePolicy, CachePolicy.CACHE_NO_NETWORK)
                && !ApiManager.isAvailable()) {
            // 1、有缓存一直取缓存； 2、无网取缓存
            request = request.newBuilder()
                    .removeHeader(CachePolicy.headerKey())
                    .removeHeader(CachePolicy.maxAgeKey())
                    .cacheControl(maxAge == ApiManager.CACHE_TIME ? DEFAULT_CACHE :
                            new CacheControl.Builder().maxStale(maxAge, TimeUnit.SECONDS).build())
                    .build();
        }

        Response response = chain.proceed(request);

        response = response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=" + maxAge)
                .build();

        return response;
    }

    /**
     * 缓存数据 - 指定有效期
     */
    private static final CacheControl DEFAULT_CACHE =
            new CacheControl.Builder().maxStale(ApiManager.CACHE_TIME, TimeUnit.SECONDS).build();
}
