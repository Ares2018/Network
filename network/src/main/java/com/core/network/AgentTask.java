package com.core.network;

import android.os.SystemClock;
import android.support.annotation.NonNull;

import com.core.network.api.ApiCall;
import com.core.network.api.ApiLoadingPage;
import com.core.network.api.ApiPreFilter;
import com.core.network.api.ApiTask;
import com.core.network.api.ApiType;
import com.core.network.cache.CachePolicy;
import com.core.network.callback.AgentCallback;
import com.core.network.callback.ApiCallback;
import com.core.network.callback.ApiProCallback;
import com.core.network.callback.ApiProgressCallback;
import com.core.network.okhttp.ProgressInterceptor;
import com.core.network.utils.ParamsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import static com.core.network.utils.HandlerUtils.runInMainThread;

/**
 * 代理 Task
 *
 * @author a_liYa
 * @date 2017/12/26 22:17.
 */
class AgentTask<T> implements Callback, AgentCallback<T> {

    private ApiCallback<T> mCallback;

    private Map<String, Object> mParamsMap; // 普通参数Map
    private Map<String, String> mFilesMap; // 文件参数Map
    private Map<String, Set<String>> mHeaders; // 请求头List

    private Object mTag;

    private ApiType mApiType;
    private ApiTask mApiTask;
    private ApiCall mTaskCall;
    private ApiLoadingPage mLoadingPage;

    private CachePolicy mCachePolicy;
    private long mStartMs; // 开始的时间 单位：毫秒
    private long mShortestMs; // 返回的最短时间 单位：毫秒

    public AgentTask(@NonNull ApiTask apiTask, ApiCallback<T> callback, ApiType type) {
        this.mApiTask = apiTask;
        this.mCallback = callback;
        this.mApiType = (type == null) ? ApiType.GET : type;
    }

    public void setTag(Object tag) {
        this.mTag = tag;
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
        if (mHeaders == null) mHeaders = new HashMap<>();
        Set<String> values = mHeaders.get(name);
        if (values == null) {
            values = new HashSet<>();
            mHeaders.put(name, values);
        }
        values.add(value);
    }

    public void setShortestMs(long shortest) {
        mShortestMs = shortest;
    }

    public void setCachePolicy(CachePolicy policy) {
        mCachePolicy = policy;
    }

    public ApiCallback<T> getCallback() {
        return mCallback;
    }

    /**
     * 请求执行操作
     */
    public ApiCall doTask() {
        mStartMs = SystemClock.uptimeMillis();

        if (mLoadingPage != null) mLoadingPage.onStart();

        List<ApiPreFilter> preFilters = ApiManager.getApiConfig().getApiPreFilters();
        boolean filterResult = false;
        if (preFilters != null) {
            for (ApiPreFilter filter : preFilters) {
                if (filter != null && filter.onFilter(mApiTask)) {
                    filterResult = true;
                }
            }
        }
        Call call = null;
        if (!filterResult) {
            Request.Builder requestBuilder = new Request.Builder();
            String url = ApiManager.getApiConfig().getUrlTransform().onUrlTransform(mApiTask
                    .getApi());

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
            ParamsBuilder.buildHeader(requestBuilder, mHeaders, mCachePolicy);

            if (mCallback instanceof ApiProgressCallback) {
                call = ApiManager.getClient().newBuilder()
                        .addInterceptor(new ProgressInterceptor((ApiProgressCallback) mCallback))
                        .build().newCall(requestBuilder.build());
            } else {
                call = ApiManager.getClient().newCall(requestBuilder.build());
            }
        }

        if (call != null) {
            call.enqueue(this);
        }
        if (mTaskCall == null) {
            mTaskCall = new ApiCall(call);
        }
        if (call != null) {
            mTaskCall.setCall(call);
            ApiCallManager.get().addCall(mTag, mTaskCall);
        }

        return mTaskCall;
    }

    // from OkHttp CallBack
    @Override
    public void onFailure(Call call, IOException e) {
        if (!ifHandleCancel(call)) {
            ApiManager.getApiConfig()
                    .getExceptionTransform().onExceptionTransform(e, this);
        }
    }

    // from OkHttp CallBack
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (!ifHandleCancel(call)) {
            ApiManager.getApiConfig()
                    .getParseResponse().onParseResponse(response, this, mApiTask);
        }
    }

    // 处理成功
    @Override
    public void onSuccess(final T result) {
        if (mCallback == null && mLoadingPage == null) {
            ApiCallManager.get().removeCall(mTag, mTaskCall); // 移除APICall
        } else {
            checkExeTime();
            runInMainThread(new Runnable() {
                @Override
                public void run() {
                    callbackSuccess(result);
                }
            });
        }
    }

    // 处理取消
    @Override
    public void onCancel() {
        if (mCallback == null && mLoadingPage == null) {
            ApiCallManager.get().removeCall(mTag, mTaskCall); // 移除APICall
        } else {
            runInMainThread(new Runnable() {
                @Override
                public void run() {
                    callbackCancel();
                }
            });
        }
    }

    // 处理失败
    @Override
    public void onError(final int errCode, final String msg) {
        if (mCallback == null && mLoadingPage == null) {
            ApiCallManager.get().removeCall(mTag, mTaskCall); // 移除APICall
        } else {
            checkExeTime();
            runInMainThread(new Runnable() {
                @Override
                public void run() {
                    callbackError(errCode, msg);
                }
            });
        }
    }

    // 回调撤消 - 主进程
    private void callbackCancel() {
        ApiCallManager.get().removeCall(mTag, mTaskCall); // 移除APICall
        if (mLoadingPage != null) {
            mLoadingPage.onCancel();
        }
        if (mCallback != null) {
            if (mCallback instanceof ApiProCallback) ((ApiProCallback) mCallback).onBefore();
            mCallback.onCancel();
            if (mCallback instanceof ApiProCallback) ((ApiProCallback) mCallback).onAfter();
        }
    }

    // 回调错误 - 主进程
    private void callbackError(int errCode, String msg) {
        if (mTaskCall != null && mTaskCall.isCanceled()) {
            callbackCancel();
            return;
        }
        ApiCallManager.get().removeCall(mTag, mTaskCall); // 移除APICall
        if (mLoadingPage != null) {
            mLoadingPage.onError(msg, errCode);
        }
        if (mCallback != null) {
            if (mCallback instanceof ApiProCallback) ((ApiProCallback) mCallback).onBefore();
            mCallback.onError(msg, errCode);
            if (mCallback instanceof ApiProCallback) ((ApiProCallback) mCallback).onAfter();
        }
    }

    // 回调成功 - 主进程
    private void callbackSuccess(T result) {
        if (mTaskCall != null && mTaskCall.isCanceled()) {
            callbackCancel();
            return;
        }
        ApiCallManager.get().removeCall(mTag, mTaskCall); // 移除APICall
        if (mLoadingPage != null) {
            mLoadingPage.onSuccess(result);
        }
        if (mCallback != null) {
            if (mCallback instanceof ApiProCallback) ((ApiProCallback) mCallback).onBefore();
            mCallback.onSuccess(result);
            if (mCallback instanceof ApiProCallback) ((ApiProCallback) mCallback).onAfter();
        }
    }

    // 检查执行时间，如果少于最短时间，则当前子线程睡眠
    private void checkExeTime() {
        // 执行时间
        long exeMs = SystemClock.uptimeMillis() - mStartMs;
        if (exeMs < mShortestMs) {
            try {
                Thread.sleep(mShortestMs - exeMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLoadingPage(ApiLoadingPage loadingPage) {
        if (loadingPage != null) {
            mLoadingPage = loadingPage;
            mLoadingPage.setApiTask(mApiTask);
        }
    }

    /**
     * 是否处理 call cancel 逻辑
     *
     * @param call OkHttp call back.
     * @return true 已处理
     */
    private boolean ifHandleCancel(Call call) {
        if (call != null && call.isCanceled()) {
            onCancel();
            return true;
        }
        return false;
    }

}
