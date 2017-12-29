package com.core.network.option.impl;

import com.core.network.option.UrlTransform;

/**
 * {@link UrlTransform}的默认实现
 *
 * @author a_liYa
 * @date 2017/12/29 11:12.
 */
public class UrlTransformImpl implements UrlTransform {

    @Override
    public String onUrlTransform(String api) {
        return api;
    }

}
