package com.core.network.callback;

import android.support.annotation.IntDef;
import android.support.annotation.WorkerThread;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ApiCallback  带进度
 *
 * @author a_liYa
 * @date 2017/12/26 17:03.
 */
public interface ApiProgressCallback {

    /**
     * 上传开始
     */
    int REQUEST_START = 1;
    /**
     * 上传进行中
     */
    int REQUEST_PROCESS = 2;
    /**
     * 上传结束
     */
    int REQUEST_END = 3;

    /**
     * 下载开始
     */
    int RESPONSE_START = 4;
    /**
     * 下载进行中
     */
    int RESPONSE_PROCESS = 5;
    /**
     * 下载结束
     */
    int RESPONSE_END = 6;

    /**
     * @param bytesRead     已进行bytes
     * @param contentLength 总bytes
     * @param state         {@link ProgressState}
     */
    @WorkerThread
    void onProgress(long bytesRead, long contentLength, @ProgressState int state);

    @IntDef(flag = true, value = {
            ApiProgressCallback.REQUEST_START,
            ApiProgressCallback.REQUEST_PROCESS,
            ApiProgressCallback.REQUEST_END,
            ApiProgressCallback.RESPONSE_START,
            ApiProgressCallback.RESPONSE_PROCESS,
            ApiProgressCallback.RESPONSE_END,
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface ProgressState {
    }

}




