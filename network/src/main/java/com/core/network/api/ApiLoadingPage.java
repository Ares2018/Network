package com.core.network.api;

/**
 * Api 加载页预留 - 接口
 *
 * @author a_liYa
 * @date 2018/1/7 11:58.
 */
public interface ApiLoadingPage {

    void setApiTask(ApiTask task);

    /**
     * 开始
     */
    void onStart();

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
    void onSuccess(Object data);

}
