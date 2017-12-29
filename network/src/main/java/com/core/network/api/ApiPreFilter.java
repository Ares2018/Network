package com.core.network.api;

import android.support.annotation.NonNull;

/**
 * 过滤器，在网络请求之前调用 - 接口
 *
 * @author a_liYa
 * @date 2017/12/29 14:17.
 */
public interface ApiPreFilter {

    boolean onFilter(@NonNull ApiTask apiTask);

}
