package com.core.network;

import com.core.network.api.ApiCall;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * ApiCall - 管理类
 *
 * @author a_liYa
 * @date 16/7/4 09:00.
 */
public class ApiCallManager {

    private volatile static ApiCallManager mInstance;

    private Map<Object, Set<ApiCall>> mCallMaps;

    private ApiCallManager() {
        mCallMaps = new HashMap<>();
    }

    /**
     * 获取实例
     * <p>
     * 单例 - 懒汉式
     *
     * @return
     */
    public static ApiCallManager get() {
        if (mInstance == null) {
            synchronized (ApiCallManager.class) {
                if (mInstance == null) {
                    mInstance = new ApiCallManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 添加Call
     *
     * @param tag  tag
     * @param call Call
     */
    public void addCall(Object tag, ApiCall call) {
        Set<ApiCall> tagCalls = mCallMaps.get(tag);
        if (tagCalls == null) {
            synchronized (ApiCallManager.class) {
                if (tagCalls == null) { // 高效比synchronized加在此方法上
                    tagCalls = Collections.synchronizedSet(new HashSet<ApiCall>());
                    mCallMaps.put(tag, tagCalls);
                }
            }
        }

        tagCalls.add(call);
    }

    /**
     * 移除tag标记的APICall集合中的call
     *
     * @param tag  标记
     * @param call call
     * @return true:删除成功  false:删除失败,包括不存在
     */
    public boolean removeCall(Object tag, ApiCall call) {
        Set<ApiCall> tagCalls = mCallMaps.get(tag);
        if (tagCalls != null) {
            boolean removeResult = tagCalls.remove(call);
            if (tagCalls.isEmpty() && tag != null)
                mCallMaps.remove(tag);

            return removeResult;
        }

        return false;
    }

    /**
     * 撤销 tag标志的APICall
     *
     * @param tag tag
     */
    public void cancel(Object tag) {
        Set<ApiCall> tagCalls = mCallMaps.get(tag);
        if (tagCalls != null && !tagCalls.isEmpty()) {
            synchronized (tagCalls) {
                for (ApiCall apiCall : tagCalls) {
                    apiCall.cancel();
                }
            }
            tagCalls.clear();
        }

        mCallMaps.remove(tag);
    }
}
