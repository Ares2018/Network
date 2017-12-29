package com.core.network.option;

import android.support.annotation.AnyThread;

/**
 * url拼接/转换 - 接口
 *
 * @author a_liYa
 * @date 2017/12/29 11:10.
 */
public interface UrlTransform {

    @AnyThread
    String onUrlTransform(String api);

}
