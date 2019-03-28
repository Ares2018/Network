package com.core.network.callback;

import android.support.annotation.WorkerThread;

import com.core.network.api.ApiState;

/**
 * ApiCallback  带进度
 *
 * @author a_liYa
 * @date 2017/12/26 17:03.
 */
public interface ApiProgressCallback {

    /**
     * @param bytesRead     已进行bytes
     * @param contentLength 总bytes
     * @param state         {@link ApiState}
     */
    @WorkerThread
    void onProgress(long bytesRead, long contentLength, ApiState state);

}




