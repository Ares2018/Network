package com.aliya.sample.task;

import com.aliya.sample.network.task.ApiPostTask;
import com.core.network.callback.ApiCallback;

/**
 * 初始化Api - task
 *
 * @author a_liYa
 * @date 2018/1/7 18:23.
 */
public class ApiInitTask extends ApiPostTask {

    public <T> ApiInitTask(ApiCallback<T> callback) {
        super(callback);
    }

    @Override
    public void onSetupParams(Object... params) {
        put("key1", "value1");
        put("key2", "value2");
    }

    @Override
    public String getApi() {
        return "/api/account/init";
    }

}
