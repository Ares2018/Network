package com.core.network;

import com.core.network.module.ExceptionTransform;
import com.core.network.module.ParseResponse;

/**
 * 网络配置相关
 *
 * @author a_liYa
 * @date 2017/12/27 21:22.
 */
public class ApiConfig {

    private ParseResponse mParseResponse;

    private ExceptionTransform mExceptionTransform;

    public ParseResponse getParseResponse() {
        return mParseResponse;
    }

    public ExceptionTransform getExceptionTransform() {
        return mExceptionTransform;
    }

    private ApiConfig(Builder builder) {
        mParseResponse = builder.mParseResponse;
        mExceptionTransform = builder.mExceptionTransform;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private ParseResponse mParseResponse;
        private ExceptionTransform mExceptionTransform;

        private Builder() {
        }

        public Builder parseResponse(ParseResponse val) {
            mParseResponse = val;
            return this;
        }

        public Builder exceptionTransform(ExceptionTransform val) {
            mExceptionTransform = val;
            return this;
        }

        public ApiConfig build() {
            return new ApiConfig(this);
        }
    }
}
