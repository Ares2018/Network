package com.aliya.core.task;

import com.core.network.BaseTask;
import com.core.network.api.ApiType;
import com.core.network.callback.ApiCallback;

public class MobileTestTask extends BaseTask {

    String token = "AAAesgAHdGVzdGFwcAAodGVzdGFwcCw4MkY1NThFNzE1RTE5QUE3RUU2QjhCN0UxQTFFODdDMQAAAC8wLQIVAIG0oB0ZM8AFf_v9pBiLqiJ7YqoAAhQUI4Xn7i3I7GyiqSBv-7xS5icmLA..";

    public <T> MobileTestTask(ApiCallback<T> callback) {
        super(callback, ApiType.GET);
    }

    @Override
    public void onSetupParams(Object... params) {
        put("methodname", "queryMetaCategorysOfIwo");
        put("serviceid", "mlf_releaseSource");
        put("test", "32@43,43");
        put("data", token);
    }

    @Override
    public String getApi() {
        return "/wcm/mlfappcenter.do";
    }

}
