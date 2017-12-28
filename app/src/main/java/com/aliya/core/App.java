package com.aliya.core;

import android.app.Application;

import com.aliya.core.network.ApiConfig;
import com.aliya.core.network.ApiManager;
import com.aliya.core.network.ParseResponseImpl;

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
                        .parseResponse(new ParseResponseImpl()));
    }
}
