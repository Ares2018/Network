package com.core.network.callback;

import android.support.annotation.MainThread;

/**
 * ApiCallback 拓展类
 *
 * @author a_liYa
 * @date 2017/12/26 16:40.
 */
public interface ApiProCallback extends ApiCallback {

    @MainThread
    void onBefore();

    @MainThread
    void onAfter();
}
