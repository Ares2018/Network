package com.aliya.sample.network;

import android.support.annotation.NonNull;

import com.core.network.ApiManager;
import com.core.network.okhttp.SSLSocketManager;
import com.core.network.option.LazyClientLoader;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * HttpClient 加载器
 *
 * @author a_liYa
 * @date 2018/1/7 18:04.
 */
public class AppClientLoader implements LazyClientLoader {

    @NonNull
    @Override
    public OkHttpClient.Builder newBuilder() {
        SSLSocketManager sslSM = new SSLSocketManager();
        return new OkHttpClient.Builder()
                .addInterceptor(new HeadersInterceptor()) // 设置Cookie拦截器
                .retryOnConnectionFailure(true)
                .sslSocketFactory(sslSM.getSSLSocketFactory(), sslSM.getX509TrustManager())
                .hostnameVerifier(sslSM.getHostnameVerifier())
                .connectTimeout(5, TimeUnit.SECONDS)  // 设置网络超时 - 连接
                .readTimeout(20, TimeUnit.SECONDS) // 设置网络超时 - 读
                .writeTimeout(20, TimeUnit.SECONDS) // 设置网络超时 - 写
                .cache(getCache()); // 缓存设置;
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
