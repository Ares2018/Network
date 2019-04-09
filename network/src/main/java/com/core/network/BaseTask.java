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

    public <T> BaseTask(ApiCallback<T> callback, ApiType type) {
        mAgentTask = new AgentTask(this, callback, type);
    }

    @Override
    @Nullable
    public ApiCall exe(Object... params) {
        this.mParams = params;
        onPreExecute();

        onSetupParams(params);

        if (onInterruptExe(params)) {
            return null;
        }

        return mAgentTask.doTask();
    }

    /**
     * 是否中断 exe() 方法
     *
     * @param params 参数
     * @return true: 中断
     */
    protected boolean onInterruptExe(Object... params) {
        return false;
    }

    @Override
    public ApiCall retryExe() {
        return exe(mParams);
    }

    /**
     * 通过 tag 来管理 task 生命周期规则如下
     * <p/>
     * tag instanceof Activity, 在 Activity#onDestroy() 方法调用时撤销请求
     * <p/>
     * tag instanceof View, 在 OnAttachStateChangeListener#onViewDetachedFromWindow(View) 时撤销请求
     *
     * @param tag 标志对象
     * @return this
     */
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
