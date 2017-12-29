package com.core.network.option.impl;

import android.support.annotation.NonNull;

import com.core.network.callback.AgentCallback;
import com.core.network.option.ExceptionTransform;

import java.io.IOException;

/**
 * {@link ExceptionTransform}的默认实现
 *
 * @author a_liYa
 * @date 2017/12/28 12:20.
 */
public class ExceptionTransformImpl implements ExceptionTransform {

    @Override
    public <T> void onExceptionTransform(IOException e, @NonNull AgentCallback<T> callback) {
        callback.onError(-1, e != null ? e.toString() : "未知异常");
        // 示例
//        if (e instanceof UnknownHostException) {
//            // Host解析失败、无网络
//        } else if (e instanceof SocketTimeoutException) {
//            // 超时异常
//        } else if (e instanceof ConnectException) {
//            // 网络连接失败(eg:代理出问题)
//        }
    }

}
