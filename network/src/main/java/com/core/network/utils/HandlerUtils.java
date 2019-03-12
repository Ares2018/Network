package com.core.network.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Handler 工具类
 *
 * @author a_liYa
 * @date 2017/12/27 11:37.
 */
public final class HandlerUtils {

    private static volatile Handler sMainHandler;

    /**
     * 在主线程中执行runnable
     *
     * @param runnable 任务
     */
    public static void runInMainThread(Runnable runnable) {
        if (runnable == null) return;
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            getMainHandler().post(runnable);
        }
    }

    /**
     * 获取主线程的handler
     *
     * @return handler
     */
    public static Handler getMainHandler() {
        if (sMainHandler == null) {
            synchronized (HandlerUtils.class) {
                if (sMainHandler == null) {
                    sMainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return sMainHandler;
    }

    /**
     * 判断当前的线程是不是在主线程
     *
     * @return true : 主线程
     */
    public static boolean isRunInMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

}
