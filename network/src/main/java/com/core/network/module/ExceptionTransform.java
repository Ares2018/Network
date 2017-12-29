package com.core.network.module;

import android.support.annotation.NonNull;

import com.core.network.callback.AgentCallback;

import java.io.IOException;

/**
 * 异常转换 - 接口
 *
 * @author a_liYa
 * @date 2017/12/27 21:28.
 */
public interface ExceptionTransform {

    //  子线程
    <T> void onExceptionTransform(IOException e, @NonNull AgentCallback<T> callback);

}
