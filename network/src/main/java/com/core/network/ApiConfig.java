package com.core.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.core.network.api.ApiPreFilter;
import com.core.network.option.ExceptionTransform;
import com.core.network.option.JsonParse;
import com.core.network.option.ParseResponse;
import com.core.network.option.UrlTransform;
import com.core.network.option.impl.ExceptionTransformImpl;
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

    private JsonParse mJsonParse;
    private UrlTransform mUrlTransform;
    private ParseResponse mParseResponse;
    private List<ApiPreFilter> mApiPreFilters;
    private ExceptionTransform mExceptionTransform;

    public @NonNull ParseResponse getParseResponse() {
        return mParseResponse;
    }

    public @NonNull UrlTransform getUrlTransform() {
        return mUrlTransform;
    }

    public @NonNull ExceptionTransform getExceptionTransform() {
        return mExceptionTransform;
    }

    public @Nullable List<ApiPreFilter> getApiPreFilters() {
        return mApiPreFilters;
    }

    public @NonNull JsonParse getJsonParse() {
        return mJsonParse;
    }

    private ApiConfig(Builder builder) {
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
                    return null;
                }
            };
        } else {
            mJsonParse = builder.jsonParse;
        }
        mApiPreFilters = builder.apiPreFilters;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private ParseResponse parseResponse;
        private UrlTransform urlTransform;
        private ExceptionTransform exceptionTransform;
        private List<ApiPreFilter> apiPreFilters;
        private JsonParse jsonParse;

        private Builder() {
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

        public ApiConfig build() {
            return new ApiConfig(this);
        }
    }
}
