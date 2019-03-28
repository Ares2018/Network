package com.aliya.sample;

import android.app.Application;

import com.aliya.sample.network.config.AppApiPreFilter;
import com.aliya.sample.network.config.AppClientLoader;
import com.aliya.sample.network.config.AppJsonParse;
import com.aliya.sample.network.config.AppUrlTransform;
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
