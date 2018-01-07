package com.core.network.api;

import android.support.annotation.AnyThread;
import android.support.annotation.MainThread;

/**
 * Api 加载页预留 - 接口
 *
 * @author a_liYa
 * @date 2018/1/7 11:58.
 */
public interface ApiLoadingPage {

    @AnyThread
    void setApiTask(ApiTask task);

    /**
     * 开始
     */
    @MainThread
    void onStart();

    /**
     * 取消
     */
    @MainThread
    void onCancel();

    /**
     * 加载失败
     */
    @MainThread
    void onError(String errMsg, int errCode);

    /**
     * 加载成功
     *
     * @param data 从网络获取的数据
     */
    @MainThread
    void onSuccess(Object data);

}
