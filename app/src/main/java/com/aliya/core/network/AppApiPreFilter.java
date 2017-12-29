package com.aliya.core.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.core.network.api.ApiPreFilter;
import com.core.network.api.ApiTask;

/**
 * 过滤器
 *
 * @author a_liYa
 * @date 2017/12/29 15:47.
 */
public class AppApiPreFilter implements ApiPreFilter {

    @Override
    public boolean onFilter(@NonNull ApiTask apiTask) {
//        Log.e("TAG", "onFilter " + apiTask.getApi());
        return false;
    }
}
