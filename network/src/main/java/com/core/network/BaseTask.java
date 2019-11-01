package com.core.network;

import android.support.annotation.Nullable;

import com.core.network.api.ApiCall;
import com.core.network.api.ApiLoadingPage;
import com.core.network.api.ApiTask;
import com.core.network.api.ApiType;
import com.core.network.cache.CachePolicy;
import com.core.network.callback.AgentCallback;
import com.core.network.callback.ApiCallback;

/**
 * Api 网络请求基类封装
 *
 * @author a_liYa
 * @date 2017/12/26 21:42.
 */
public abstract class BaseTask implements ApiTask {

    private Object[] mParams;
    private AgentTask mAgentTask;

    private char[] except;

    public <T> BaseTask(ApiCallback<T> callback, ApiType type) {
        mAgentTask = new AgentTask(this, callback, type);
    }

    @Override
    public ApiTask setURLEncodeExcept(char[] chars) {
        this.except = chars;
        return this;
    }

    @Override
    public @Nullable ApiCall exe(Object... params) {
        this.mParams = params;
        onPreExecute();

        onSetupParams(params);

        if (onInterruptExe(params)) {
            return null;
        }
        mAgentTask.setURLEncodeExcept(except);

        return mAgentTask.doTask();
    }

    protected boolean onInterruptExe(Object... params) {
        return false;
    }

    @Override
    public ApiCall retryExe() {
        return exe(mParams);
    }

    public ApiTask setTag(Object tag) {
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

    @Override
    public ApiTask setLoadingPage(ApiLoadingPage loadingPage) {
        mAgentTask.setLoadingPage(loadingPage);
        return this;
    }

    @Override
    public <T> ApiCallback<T> getCallback() {
        return mAgentTask.getCallback();
    }

    public <T> AgentCallback<T> getAgentCallback() {
        return mAgentTask;
    }

    protected void onPreExecute() {
    }

}
