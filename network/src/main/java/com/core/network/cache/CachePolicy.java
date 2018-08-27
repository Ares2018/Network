package com.core.network.cache;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 缓存 策略
 *
 * @author a_liYa
 * @date 2017/9/6 15:07.
 */
public final class CachePolicy {

    public static final String defaultValue() {
        return CACHE_NO_NETWORK;
    }

    private static final String HEADER_NAME = "cache-policy";

    /**
     * The key of the header
     *
     * @return string
     */
    public static final String headerKey() {
        return HEADER_NAME;
    }

    /**
     * The key of the max age
     *
     * @return string
     */
    public static final String maxAgeKey() {
        return "max-age";
    }


    public static final int MAX_AGE_NO_VALUE = -1;
    public String cachePolicy;
    public int cacheMaxAge = MAX_AGE_NO_VALUE;

    public CachePolicy(@PolicyMode String cachePolicy) {
        this.cachePolicy = cachePolicy;
    }

    public CachePolicy(int cacheMaxAge, @PolicyMode String cachePolicy) {
        this.cacheMaxAge = cacheMaxAge;
        this.cachePolicy = cachePolicy;
    }


    /**
     * 无网取缓存，有网走网络
     */
    public static final String CACHE_NO_NETWORK = "cache_no_network";
    /**
     * 永不取缓存
     */
    public static final String NO_CACHE = "no_cache";
    /**
     * 有缓存一直取缓存
     */
    public static final String CACHE_ONLY = "cache_only";

    @StringDef(value = {
            CACHE_NO_NETWORK,
            NO_CACHE,
            CACHE_ONLY
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PolicyMode {
    }

}
