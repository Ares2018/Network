package com.aliya.sample.network.task;

import com.core.network.BaseTask;
import com.core.network.api.ApiType;
import com.core.network.callback.ApiCallback;

/**
 * Api Post json content 类型 - 请求基类
 *
 * @author a_liYa
 * @date 2019/3/27 14:52.
 */
public abstract class ApiPostTask extends BaseTask {

    public <T> ApiPostTask(ApiCallback<T> callback) {
        super(callback, ApiType.POST);
    }

}
