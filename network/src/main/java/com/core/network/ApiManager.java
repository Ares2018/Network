package com.core.network;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.core.network.cache.CacheInterceptor;
import com.core.network.okhttp.SSLSocketManager;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Api Manager
 *
 * @author a_liYa
 * @date 2017/12/26 11:02.
 */
public class ApiManager {

    private static Context sContext;
    private static OkHttpClient sClient;

    public static final int CACHE_TIME = 3600 * 24 * 30; // 缓存时间 单位：秒；默认30天
    public static final int CACHE_MAX_SIZE = 10 * 1024 * 1024; // 缓存大小；默认10M

    public static void setContext(Context context) {
        if (context != null) {
            sContext = context.getApplicationContext();
            if (sContext instanceof Application) {
                ((Application) sContext)
                        .registerActivityLifecycleCallbacks(new LifecycleCallbacks());
            }
        }
    }

    public static void clientBuild(OkHttpClient.Builder builder) {
        if (builder != null) {
            sClient = builder
                    .addInterceptor(new CacheInterceptor())
                    .build();
        }
    }

    public static OkHttpClient getClient() {
        if (sClient == null) {
            synchronized (ApiManager.class) {
                SSLSocketManager sslSM = new SSLSocketManager();
                sClient = new OkHttpClient.Builder()
                        .retryOnConnectionFailure(true)
                        .sslSocketFactory(sslSM.getSSLSocketFactory(), sslSM.getX509TrustManager())
                        .hostnameVerifier(sslSM.getHostnameVerifier())
                        .cache(new Cache(
                                new File(sContext.getCacheDir(), "HttpCache"), CACHE_MAX_SIZE))
                        .addInterceptor(new CacheInterceptor())
                        .build();
            }
        }
        return sClient;
    }

    private static ApiConfig sApiConfig;

    public static void configBuild(ApiConfig.Builder builder) {
        sApiConfig = builder.build();
    }

    public static ApiConfig getApiConfig() {
        if (sApiConfig == null) {
            synchronized (ApiManager.class) {
                if (sApiConfig == null) {
                    sApiConfig = ApiConfig.newBuilder().build();
                }
            }
        }
        return sApiConfig;
    }

    /**
     * 网络是否可用
     *
     * @return true:可用
     */
    public static boolean isAvailable() {
        if (sContext == null) return true;
        NetworkInfo networkInfo = ((ConnectivityManager)
                sContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    static class LifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            ApiCallManager.get().cancel(activity);
        }

    }

}