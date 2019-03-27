package com.aliya.sample.network.base;

import com.core.network.BaseTask;
import com.core.network.api.ApiType;
import com.core.network.callback.ApiCallback;

/**
 * Api Get - 请求基类
 *
 * @author a_liYa
 * @date 2019/3/27 14:49.
 */
public abstract class ApiGetTask extends BaseTask {

    public <T> ApiGetTask(ApiCallback<T> callback) {
        super(callback, ApiType.GET);
    }

}
