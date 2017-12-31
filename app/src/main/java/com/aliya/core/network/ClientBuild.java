package com.aliya.core.network;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.core.network.okhttp.SSLSocketManager;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Client build 工具
 *
 * @author a_liYa
 * @date 2017/12/31 10:24.
 */
public class ClientBuild {

    public static OkHttpClient.Builder newBuilder(Context context) {
        long millis = SystemClock.uptimeMillis();
        SSLSocketManager sslSM = new SSLSocketManager();
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                .addInterceptor(new HeadersInterceptor()) // 设置Cookie拦截器
                .retryOnConnectionFailure(true)
                .sslSocketFactory(sslSM.getSSLSocketFactory(), sslSM.getX509TrustManager())
                .hostnameVerifier(sslSM.getHostnameVerifier())
                .connectTimeout(5, TimeUnit.SECONDS)  // 设置网络超时 - 连接
                .readTimeout(20, TimeUnit.SECONDS) // 设置网络超时 - 读
                .writeTimeout(20, TimeUnit.SECONDS) // 设置网络超时 - 写
                .cache(getCache(context)); // 缓存设置
        Log.e("TAG", "创建 ClientBuilder 耗时：" + (SystemClock.uptimeMillis() - millis));
        return builder;
    }

    public static final String CACHE_FILENAME = "HttpCache";
    public static final int CACHE_MAX_SIZE = 10 * 1024 * 1024; // 10M

    /**
     * 获取缓存设置
     *
     * @return 缓存对象
     */
    private static Cache getCache(Context context) {
        // /data/user/0/包名/cache
        File httpCacheDirectory = new File(context.getCacheDir(), CACHE_FILENAME);
        return new Cache(httpCacheDirectory, CACHE_MAX_SIZE); // 参一:缓存目录; 参二:缓存最大容量
    }

}
