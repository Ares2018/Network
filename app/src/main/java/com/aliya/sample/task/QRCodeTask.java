package com.aliya.sample.task;

import com.aliya.sample.network.task.ApiGetTask;
import com.core.network.callback.ApiCallback;

/**
 * 查询二维码
 *
 * @author a_liYa
 * @date 2017/12/28 12:43.
 */
public class QRCodeTask extends ApiGetTask {

    public <T> QRCodeTask(ApiCallback<T> callback) {
        super(callback);
    }

    @Override
    public void onSetupParams(Object... params) {
        put("key", "1234556");
        put("type", 2);
        put("fgcolor", "00b7ee");
        put("w", 90);
        put("m", 5);
        put("text", "hello world");
    }

    @Override
    public String getApi() {
        return "http://apis.juhe.cn/qrcode/api";
    }
}
