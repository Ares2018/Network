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

    public ApiCall(Call mCall) {
        this.mCall = mCall;
    }

    public Call getCall() {
        return mCall;
    }

    public void setCall(Call mCall) {
        this.mCall = mCall;
    }

    public void cancel() {
        if (mCall != null)
            mCall.cancel();
    }

    public boolean isExecuted() {
        if (mCall == null)
            return false;

        return mCall.isExecuted();
    }

    public boolean isCanceled() {
        if (mCall == null)
            return true;
        return mCall.isCanceled();
    }

}
