package com.aliya.core.network;

import com.aliya.core.network.api.ApiCall;
import com.aliya.core.network.api.ApiTask;
import com.aliya.core.network.api.ApiType;
import com.aliya.core.network.cache.CachePolicy;
import com.aliya.core.network.callback.ApiCallback;

/**
 * Api 网络请求基类封装
 *
 * @author a_liYa
 * @date 2017/12/26 21:42.
 */
public abstract class BaseTask<T> implements ApiTask {

    private Object[] mParams;
    private ApiCall mTaskCall;
    private AgentTask mAgentTask;

    public BaseTask(ApiCallback<T> callback, ApiType type) {
        mAgentTask = new AgentTask(this, callback, type);
    }

    @Override
    public ApiCall exe(Object... params) {
        this.mParams = params;
        onPreExecute();

        onSetupParams(params);

        mTaskCall = mAgentTask.onBackTask();

        return null;
    }

    @Override
    public ApiCall retryExe() {
        return exe(mParams);
    }

    public BaseTask<T> setTag(Object tag) {
        mAgentTask.setTag(tag);
        return this;
    }

    @Override
    public ApiTask put(String key, Object value) {
        mAgentTask.put(key, value);
        return this;
    }

    @Override
    public ApiTask putFile(String key, String filePath) {
        mAgentTask.putFile(key, filePath);
        return this;
    }

    @Override
    public ApiTask addHeader(String name, String value) {
        mAgentTask.addHeader(name, value);
        return this;
    }

    @Override
    public ApiTask setShortestMs(long shortest) {
        mAgentTask.setShortestMs(shortest);
        return this;
    }

    @Override
    public ApiTask setCachePolicy(CachePolicy policy) {
        mAgentTask.setCachePolicy(policy);
        return this;
    }

    protected void onPreExecute() {
    }

}
