package com.core.network.callback;

/**
 * Api接口回调
 *
 * @param <T> 泛型 ：加载成功返回的数据类型
 * @author a_liYa
 * @date 2017/12/26 16:21.
 */
public interface ApiCallback<T> {

    /**
     * 取消
     */
    void onCancel();

    /**
     * 加载失败
     */
    void onError(String errMsg, int errCode);

    /**
     * 加载成功
     *
     * @param data 从网络获取的数据
     */
    void onSuccess(T data);

}
