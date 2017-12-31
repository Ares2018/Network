package com.core.network.option;

import java.lang.reflect.Type;

/**
 * Json解析 - 接口
 *
 * @author a_liYa
 * @date 2017/12/29 21:34.
 */
public interface JsonParse {

    <T> T onJsonParse(String text, Type typeOf);

}
