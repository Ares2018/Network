package com.core.network.option;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;

/**
 * 延迟HttpClient加载器 - 接口
 *
 * @author a_liYa
 * @date 2017/12/31 10:55.
 */
public interface LazyClientLoader {

    @NonNull
    OkHttpClient.Builder newBuilder();

}
