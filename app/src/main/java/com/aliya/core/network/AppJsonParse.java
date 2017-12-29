package com.aliya.core.network;

import com.alibaba.fastjson.JSON;
import com.core.network.option.JsonParse;

import java.lang.reflect.Type;

/**
 * 自定义 Json 解析的实现类
 *
 * @author a_liYa
 * @date 2017/12/29 22:00.
 */
public class AppJsonParse implements JsonParse {

    @Override
    public <T> T onJsonParse(String text, Type type) {
        return JSON.parseObject(text, type);
    }

}
