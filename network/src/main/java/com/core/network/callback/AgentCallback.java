package com.core.network.callback;

/**
 * 代理 callback
 *
 * @author a_liYa
 * @date 2017/12/27 21:04.
 */
public interface AgentCallback<T> {

    void onSuccess(T result);

    void onCancel();

    void onError(int errCode, String msg);

}
