package com.aliya.core.network;

import android.support.annotation.NonNull;

import com.aliya.core.network.callback.AgentCallback;
import com.aliya.core.network.module.ExceptionTransform;

import java.io.IOException;

/**
 * ExceptionTransform的示例
 *
 * @author a_liYa
 * @date 2017/12/28 12:20.
 */
public class ExceptionTransformImple implements ExceptionTransform {

    @Override
    public <T> void onExceptionTransform(IOException e, @NonNull AgentCallback<T> callback) {
        callback.onError(-1, e != null ? e.toString() : "未知异常");
    }

}
