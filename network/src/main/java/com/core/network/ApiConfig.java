package com.core.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.core.network.api.ApiPreFilter;
import com.core.network.option.ExceptionTransform;
import com.core.network.option.JsonParse;
import com.core.network.option.LazyClientLoader;
import com.core.network.option.ParseResponse;
import com.core.network.option.UrlTransform;
import com.core.network.option.impl.ExceptionTransformImpl;
import com.core.network.option.impl.LazyClientLoaderImpl;
import com.core.network.option.impl.ParseResponseImpl;
import com.core.network.option.impl.UrlTransformImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 网络配置相关
 *
 * @author a_liYa
 * @date 2017/12/27 21:22.
 */
public class ApiConfig {

    private int mCacheTime; // 缓存时间 单位:秒

    private JsonParse mJsonParse;
    private UrlTransform mUrlTransform;
    private ParseResponse mParseResponse;
    private List<ApiPreFilter> mApiPreFilters;
    private ExceptionTransform mExceptionTransform;

    private LazyClientLoader mLazyClientLoader;

    public int getCacheTime() {
        return mCacheTime;
    }

    @NonNull
    public ParseResponse getParseResponse() {
        return mParseResponse;
    }

    @NonNull
    public UrlTransform getUrlTransform() {
        return mUrlTransform;
    }

    @NonNull
    public ExceptionTransform getExceptionTransform() {
        return mExceptionTransform;
    }

    @Nullable
    public List<ApiPreFilter> getApiPreFilters() {
        return mApiPreFilters;
    }

    @NonNull
    public JsonParse getJsonParse() {
        return mJsonParse;
    }

    @NonNull
    public LazyClientLoader getLazyClientLoader() {
        return mLazyClientLoader;
    }

    private ApiConfig(Builder builder) {
        mCacheTime = builder.cacheTime;
        if (null == builder.urlTransform) {
            mUrlTransform = new UrlTransformImpl();
        } else {
            mUrlTransform = builder.urlTransform;
        }
        if (null == builder.parseResponse) {
            mParseResponse = new ParseResponseImpl();
        } else {
            mParseResponse = builder.parseResponse;
        }
        if (null == builder.exceptionTransform) {
            mExceptionTransform = new ExceptionTransformImpl();
        } else {
            mExceptionTransform = builder.exceptionTransform;
        }
        if (null == builder.jsonParse) {
            mJsonParse = new JsonParse() {
                @Override
                public <T> T onJsonParse(String text, Type type) {
                    if (ApiManager.isDebuggable()) {
                        Log.e("TAG", "JsonParse 接口必须被实现 ");
                    }
                    return null;
                }

                @Override
                public String onJsonString(Object obj) {
                    if (ApiManager.isDebuggable()) {
                        Log.e("TAG", "JsonParse 接口必须被实现 ");
                    }
                    return "{}";
                }
            };
        } else {
            mJsonParse = builder.jsonParse;
        }
        if (null == builder.lazyClientLoader) {
            mLazyClientLoader = new LazyClientLoaderImpl();
        } else {
            mLazyClientLoader = builder.lazyClientLoader;
        }
        mApiPreFilters = builder.apiPreFilters;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private int cacheTime = 3600 * 24 * 30; // 默认30天

        private ParseResponse parseResponse;
        private UrlTransform urlTransform;
        private ExceptionTransform exceptionTransform;
        private List<ApiPreFilter> apiPreFilters;
        private JsonParse jsonParse;

        private LazyClientLoader lazyClientLoader;

        private Builder() {
        }

        public Builder cacheTime(int cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        public Builder parseResponse(ParseResponse parseResponse) {
            this.parseResponse = parseResponse;
            return this;
        }

        public Builder urlTransform(UrlTransform urlTransform) {
            this.urlTransform = urlTransform;
            return this;
        }

        public Builder exceptionTransform(ExceptionTransform exceptionTransform) {
            this.exceptionTransform = exceptionTransform;
            return this;
        }

        public Builder jsonParse(JsonParse jsonParse) {
            this.jsonParse = jsonParse;
            return this;
        }

        public Builder addApiPreFilter(ApiPreFilter filter) {
            if (filter != null) {
                if (apiPreFilters == null) {
                    apiPreFilters = new ArrayList<>();
                }
                apiPreFilters.add(filter);
            }
            return this;
        }

        public Builder lazyClientLoader(LazyClientLoader lazyClientLoader) {
            this.lazyClientLoader = lazyClientLoader;
            return this;
        }

        public ApiConfig build() {
            return new ApiConfig(this);
        }
    }
}
