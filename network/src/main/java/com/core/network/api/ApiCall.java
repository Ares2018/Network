package com.core.network.api;

import okhttp3.Call;

/**
 * Call的二次封装(门面模式)
 *
 * @author a_liYa
 * @date 2017/12/26 21:39.
 */
public class ApiCall {

    private Call mCall;
    private boolean isCanceled;

    public ApiCall(Call call) {
        this.mCall = call;
    }

    public Call getCall() {
        return mCall;
    }

    public void setCall(Call call) {
        this.mCall = call;
    }

    public void cancel() {
        if (mCall != null) mCall.cancel();
        isCanceled = true;
    }

    public boolean isExecuted() {
        return mCall != null && mCall.isExecuted();
    }

    public boolean isCanceled() {
        return isCanceled || (mCall != null && mCall.isCanceled());
    }
}
