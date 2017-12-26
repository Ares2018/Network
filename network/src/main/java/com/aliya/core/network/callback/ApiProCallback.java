package com.aliya.core.network.callback;

/**
 * ApiCallback 拓展类
 *
 * @author a_liYa
 * @date 2017/12/26 16:40.
 */
public interface ApiProCallback extends ApiCallback {

    void onBefore();

    void onAfter();
}
