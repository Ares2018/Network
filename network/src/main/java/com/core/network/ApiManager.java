package com.core.network;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.util.Log;

import com.core.network.cache.CacheInterceptor;

import okhttp3.OkHttpClient;

/**
 * Api Manager
 *
 * @author a_liYa
 * @date 2017/12/26 11:02.
 */
public class ApiManager {

    private static Context sContext;
    private static boolean sDebuggable;
    private static ApiConfig sApiConfig;
    private static OkHttpClient sHttpClient;

    public static final String LOG_TAG = "API_LOG";

    /**
     * 初始化
     *
     * @param context Context
     * @see #init(Context, ApiConfig.Builder)
     */
    public static void init(Context context) {
        init(context, null);
    }

    public static void init(Context context, ApiConfig.Builder configBuilder) {
        if (context != null && sContext == null) {
            sContext = context.getApplicationContext();
            if (sContext instanceof Application) {
                ((Application) sContext)
                        .registerActivityLifecycleCallbacks(new LifecycleCallbacks());
            }
            try {
                sDebuggable = (sContext.getPackageManager()
                        .getPackageInfo(sContext.getPackageName(), PackageManager.GET_ACTIVITIES)
                        .applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            } catch (Exception e) {
            }
        }
        if (configBuilder != null && sApiConfig == null) {
            sApiConfig = configBuilder.build();
        }
        Log.e("TAG", "addIdleHandler " + SystemClock.uptimeMillis());
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                long l = SystemClock.uptimeMillis();
                Log.e("TAG", "queueIdle " + l);
                getClient();
                Log.e("TAG", "queueIdle 耗时: " + (SystemClock.uptimeMillis() - l));
                return false;
            }
        });
    }

    public static OkHttpClient getClient() {
        if (sHttpClient == null) {
            synchronized (ApiManager.class) {
                if (sHttpClient == null && sApiConfig != null) {
                    sHttpClient = sApiConfig.getLazyClientLoader().newBuilder()
                            .addInterceptor(new CacheInterceptor())
                            .build();
                }
            }
        }
        return sHttpClient;
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

    public static Context getContext() {
        return sContext;
    }

    public static boolean isDebuggable() {
        return sDebuggable;
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