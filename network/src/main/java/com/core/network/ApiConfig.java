package com.core.network;

import com.core.network.api.ApiPreFilter;
import com.core.network.option.ExceptionTransform;
import com.core.network.option.ParseResponse;
import com.core.network.option.UrlTransform;
import com.core.network.option.impl.ExceptionTransformImpl;
import com.core.network.option.impl.ParseResponseImpl;
import com.core.network.option.impl.UrlTransformImpl;

import java.util.List;

/**
 * 网络配置相关
 *
 * @author a_liYa
 * @date 2017/12/27 21:22.
 */
public class ApiConfig {

    private ParseResponse mParseResponse;

    private UrlTransform mUrlTransform;

    private ExceptionTransform mExceptionTransform;

    private List<ApiPreFilter> mApiPreFilters;

    public ParseResponse getParseResponse() {
        return mParseResponse;
    }

    public UrlTransform getUrlTransform() {
        return mUrlTransform;
    }

    public ExceptionTransform getExceptionTransform() {
        return mExceptionTransform;
    }

    public List<ApiPreFilter> getApiPreFilters() {
        return mApiPreFilters;
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
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private ParseResponse parseResponse;
        private UrlTransform urlTransform;
        private ExceptionTransform exceptionTransform;

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

        public ApiConfig build() {
            return new ApiConfig(this);
        }
    }
}
