package com.aliya.core.task;

import com.core.network.BaseTask;
import com.core.network.api.ApiType;
import com.core.network.callback.ApiCallback;

public class MobileTest extends BaseTask {

    public <T> MobileTest(ApiCallback<T> callback) {
        super(callback, ApiType.POST);
    }

    @Override
    public void onSetupParams(Object... params) {
        put("UserName", "testapp");
        put("Password", "12345678");
    }

    @Override
    public String getApi() {
        return "/wcm/security/applogin";
    }

}
