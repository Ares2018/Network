package com.aliya.sample.network.task;

import com.core.network.BaseTask;
import com.core.network.api.ApiType;
import com.core.network.callback.ApiCallback;

/**
 * Api Upload form 类型 - 请求基类
 *
 * @author a_liYa
 * @date 2019/3/28 15:17.
 */
public abstract class ApiUploadTask extends BaseTask {

    public <T> ApiUploadTask(ApiCallback<T> callback) {
        super(callback, ApiType.POST_UPLOAD);
    }
}
