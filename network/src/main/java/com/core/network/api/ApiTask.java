package com.core.network.api;

import com.core.network.cache.CachePolicy;
import com.core.network.callback.ApiCallback;

/**
 * Api task
 *
 * @author a_liYa
 * @date 2017/12/26 21:33.
 */
public interface ApiTask {

    ApiCall exe(Object... params);

    ApiCall retryExe(boolean force);

    void onSetupParams(Object... params);

    String getApi();

    <T> ApiCallback<T> getCallback();

    ApiTask put(String key, Object value);

    ApiTask putFile(String key, String filePath);

    ApiTask addHeader(String name, String value);

    ApiTask setShortestMs(long shortest);

    ApiTask setCachePolicy(CachePolicy policy);

    ApiTask setLoadingPage(ApiLoadingPage loadingPage);

}
