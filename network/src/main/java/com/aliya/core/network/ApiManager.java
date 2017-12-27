package com.aliya.core.network;

import okhttp3.OkHttpClient;

/**
 * Api Manager
 *
 * @author a_liYa
 * @date 2017/12/26 11:02.
 */
public class ApiManager {

    private static OkHttpClient sClient;

    public static OkHttpClient getClient() {
        return sClient;
    }
}