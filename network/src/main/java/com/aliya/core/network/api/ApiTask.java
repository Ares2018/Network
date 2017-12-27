package com.aliya.core.network.api;

import com.aliya.core.network.cache.CachePolicy;

/**
 * Api task
 *
 * @author a_liYa
 * @date 2017/12/26 21:33.
 */
public interface ApiTask {

    ApiCall exe(Object... params);

    ApiCall retryExe();

    void onSetupParams(Object... params);

    String getApi();

    ApiTask put(String key, Object value);

    ApiTask putFile(String key, String filePath);

    ApiTask addHeader(String name, String value);

    ApiTask setShortestMs(long shortest);

    ApiTask setCachePolicy(CachePolicy policy);

}
