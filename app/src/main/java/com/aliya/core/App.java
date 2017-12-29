package com.aliya.core;

import android.app.Application;

import com.aliya.core.network.AppUrlTransform;
import com.core.network.ApiConfig;
import com.core.network.ApiManager;

/**
 * Application
 *
 * @author a_liYa
 * @date 2017/12/28 11:45.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiManager.setContext(this);
        ApiManager.configBuild(
                ApiConfig.newBuilder()
                        .urlTransform(new AppUrlTransform()));
    }
}
