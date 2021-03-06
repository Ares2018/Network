package com.core.network.callback;

import android.support.annotation.MainThread;

/**
 * Api接口回调
 *
 * @param <EntityType> 泛型 ：加载成功返回的数据类型
 * @author a_liYa
 * @date 2017/12/26 16:21.
 */
public interface ApiCallback<EntityType> {

    /**
     * 取消
     */
    @MainThread
    void onCancel();

    /**
     * 加载失败
     *
     * @param errMsg  .
     * @param errCode .
     */
    @MainThread
    void onError(String errMsg, int errCode);

    /**
     * 加载成功
     *
     * @param data 从网络获取的数据
     */
    @MainThread
    void onSuccess(EntityType data);

}
