package com.core.network.option.impl;

import android.support.annotation.NonNull;

import com.core.network.ApiManager;
import com.core.network.okhttp.SSLSocketManager;
import com.core.network.option.LazyClientLoader;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * {@link LazyClientLoader}的默认实现
 *
 * @author a_liYa
 * @date 2017/12/31 11:06.
 */
public class LazyClientLoaderImpl implements LazyClientLoader {

    @NonNull
    @Override
    public OkHttpClient.Builder newBuilder() {
        SSLSocketManager sslSM = new SSLSocketManager();
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .sslSocketFactory(sslSM.getSSLSocketFactory(), sslSM.getX509TrustManager())
                .hostnameVerifier(sslSM.getHostnameVerifier())
                .cache(getCache());
    }

    public static final String CACHE_FILENAME = "HttpCache";
    public static final int CACHE_MAX_SIZE = 10 * 1024 * 1024; // 10M

    /**
     * 获取缓存设置
     *
     * @return 缓存对象
     */
    private static Cache getCache() {
        // /data/user/0/包名/cache
        File httpCacheDirectory = new File(ApiManager.getContext().getCacheDir(), CACHE_FILENAME);
        return new Cache(httpCacheDirectory, CACHE_MAX_SIZE); // 参一:缓存目录; 参二:缓存最大容量
    }
}
