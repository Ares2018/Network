package com.aliya.core.network;

import android.os.SystemClock;

import com.aliya.core.network.api.ApiCall;
import com.aliya.core.network.api.ApiTask;
import com.aliya.core.network.api.ApiType;
import com.aliya.core.network.callback.ApiCallback;
import com.aliya.core.network.callback.ApiProCallback;
import com.aliya.core.network.callback.ApiProgressCallback;
import com.aliya.core.network.utils.ParamsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 代理 Task
 *
 * @author a_liYa
 * @date 2017/12/26 22:17.
 */
class AgentTask<T> implements Callback {

    private ApiCallback<T> mCallback;

    private Map<String, Object> mParamsMap; // 普通参数Map
    private Map<String, String> mFilesMap; // 文件参数Map
    private List<Header> mHeaders; // 请求头List

    private Object tag;

    private ApiType mApiType;
    private ApiTask mApiTask;
    private ApiCall mTaskCall;

    private long startTime; // 开始执行的时间

    public AgentTask(ApiTask apiTask, ApiCallback<T> callback, ApiType type) {
        this.mApiTask = apiTask;
        this.mCallback = callback;
        this.mApiType = (type == null) ? ApiType.GET : type;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public void put(String key, Object value) {
        if (mParamsMap == null) mParamsMap = new HashMap<>();
        mParamsMap.put(key, value);
    }

    public void putFile(String key, String filePath) {
        if (mFilesMap == null) mFilesMap = new HashMap<>();
        mFilesMap.put(key, filePath);
    }

    public void addHeader(String name, String value) {
        if (mHeaders == null) mHeaders = new ArrayList<>();
        mHeaders.add(new Header(name, value));
    }

    /**
     * 请求执行操作
     */
    public ApiCall onBackTask() {
        startTime = SystemClock.uptimeMillis();

        Request.Builder requestBuilder = new Request.Builder();
        Call call;

        String url = mApiTask.getApi();
        switch (mApiType) {
            case GET:
                requestBuilder.url(ParamsBuilder.buildGet(mParamsMap, url));
                break;
            case POST:
//              RequestBody requestBody = RequestBody.create(
//                      MediaType.parse("application/json; charset=utf-8"), jsonString(mParamsMap));
                requestBuilder.url(url).post(ParamsBuilder.buildPost(mParamsMap));
                break;
            case POST_UPLOAD:
                requestBuilder.url(url).post(ParamsBuilder.buildUpload(mParamsMap, mFilesMap));
                break;
        }
        buildHeader(requestBuilder);

        if (mCallback instanceof ApiProgressCallback) {
            call = ApiManager.getClient().newBuilder()
//                    .addInterceptor(new ProgressInterceptor(listener))
                    .build().newCall(requestBuilder.build());
        } else {
            call = ApiManager.getClient().newCall(requestBuilder.build());
        }

        if (call != null) {
            call.enqueue(this);
        }
        if (mTaskCall == null) {
            mTaskCall = new ApiCall(call);
        }
        mTaskCall.setCall(call);
        ApiCallManager.get().addCall(tag, mTaskCall);
        return mTaskCall;
    }

    /**
     * 增加Header
     *
     * @param request 请求Request.Builder
     */
    private void buildHeader(Request.Builder request) {
        if (request == null) return;

        if (mHeaders != null && !mHeaders.isEmpty()) { // 设置Header
            for (Header header : mHeaders) {
                request.addHeader(header.name, header.value);
            }
        }

//        // 封装的缓存策略
//        if (!TextUtils.isEmpty(cachePolicy)) {
//            request.header(CachePolicy.headerKey(), cachePolicy);
//        }
//        if (cacheMaxAge > -1) {
//            request.header(CachePolicy.maxAgeKey(), String.valueOf(cacheMaxAge));
//        }

    }

    @Override
    public void onFailure(Call call, IOException e) {
        if (mCallback != null) {
            if (call.isCanceled()) {
//                handleCancel();
            } else {
//                handleError(e);
            }
        } else {
            ApiCallManager.get().removeCall(tag, mTaskCall); // 移除APICall
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (call != null && call.isCanceled()) {
//            handleCancel();
        } else {

        }
    }

    // 回调撤消 - 主进程
    private void callbackCancel() {
        ApiCallManager.get().removeCall(tag, mTaskCall); // 移除APICall
//        if (mLoadViewHolder != null) {
//            mLoadViewHolder.showFailed(APICode.CANCEL);
//        }
        if (mCallback instanceof ApiProCallback) {
            ((ApiProCallback) mCallback).onBefore();
        }
        mCallback.onCancel();
        if (mCallback instanceof ApiProCallback) {
            ((ApiProCallback) mCallback).onAfter();
        }
    }

    // 回调错误 - 主进程
    private void callbackError(int errCode, String msg) {
        ApiCallManager.get().removeCall(tag, mTaskCall); // 移除APICall
        if (mTaskCall != null && mTaskCall.isCanceled()) {
            callbackCancel();
            return;
        }
//        if (mLoadViewHolder != null) {
//            mLoadViewHolder.showFailed(errCode);
//        }
        if (mCallback instanceof ApiProCallback) {
            ((ApiProCallback) mCallback).onBefore();
        }
        mCallback.onError(msg, errCode);
        if (mCallback instanceof ApiProCallback) {
            ((ApiProCallback) mCallback).onAfter();
        }
    }

    // 回调成功
    private void callbackSuccess(T result) {
        ApiCallManager.get().removeCall(tag, mTaskCall); // 移除APICall
        if (mTaskCall != null && mTaskCall.isCanceled()) {
            callbackCancel();
            return;
        }
//        if (mLoadViewHolder != null) {
//            mLoadViewHolder.finishLoad();
//        }
        if (mCallback instanceof ApiProCallback) {
            ((ApiProCallback) mCallback).onBefore();
        }
        mCallback.onSuccess(result);
        if (mCallback instanceof ApiProCallback) {
            ((ApiProCallback) mCallback).onAfter();
        }
    }


    /**
     * 请求头简单封装
     *
     * @author a_liYa
     * @date 16/8/8 下午5:53.
     */
    private static final class Header {
        String name;
        String value;

        public Header(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

}
