package com.aliya.core;

import android.app.Application;

import com.aliya.core.network.AppApiPreFilter;
import com.aliya.core.network.AppClientLoader;
import com.aliya.core.network.AppJsonParse;
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
        ApiManager.init(this, ApiConfig.newBuilder()
                .urlTransform(new AppUrlTransform())
                .addApiPreFilter(new AppApiPreFilter())
                .lazyClientLoader(new AppClientLoader())
                .jsonParse(new AppJsonParse()));
    }

}
