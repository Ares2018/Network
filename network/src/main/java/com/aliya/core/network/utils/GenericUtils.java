package com.aliya.core.network.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 泛型 - 工具类
 *
 * @author a_liYa
 * @date 2017/9/28 16:19.
 */
public final class GenericUtils {

    /**
     * 获取类申明的泛型
     *
     * @param clazz 目标类
     * @return 返回泛型集合 （可能有多个泛型 eg：Map<K, V>）
     */
    public static List<Class> getGenericClass(Class clazz) {
        List<Class> classes = new ArrayList<>();

        while (clazz != Object.class) {

            Type t = clazz.getGenericSuperclass();
            if (t instanceof ParameterizedType) {
                Type[] args = ((ParameterizedType) t).getActualTypeArguments();
                if (args[0] instanceof Class) {
                    classes.add((Class) args[0]);
                    return classes;
                } else if (args[0] instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) args[0];

                    if (pt.getRawType() instanceof Class) {
                        classes.add((Class) pt.getRawType());
                    }

                    Type[] atArgs = pt.getActualTypeArguments();
                    for (Type type : atArgs) {
                        if (type instanceof Class) {
                            classes.add((Class) type);
                        }
                    }
                    return classes;
                }
            }
            clazz = clazz.getSuperclass();
        }

        return classes;
    }

}
