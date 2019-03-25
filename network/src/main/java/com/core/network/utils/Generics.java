package com.core.network.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型 - 工具类
 *
 * @author a_liYa
 * @date 2017/9/28 16:19.
 */
public final class Generics {

    private Generics() {
    }

//    /**
//     * 获取类声明的泛型
//     *
//     * @param clazz 目标类
//     * @return 返回泛型对应type {@link Type}, 获取失败返回null.
//     */
//    @Nullable
//    public static Type getGenericType(Class clazz) {
//        while (clazz != Object.class) {
//            Type type;
//            Type[] interfaces = clazz.getGenericInterfaces();
//            if (interfaces != null && interfaces.length > 0) { // 接口上的实际类型参数
//                type = interfaces[0];
//            } else { // 类/抽象类上的实际类型参数
//                type = clazz.getGenericSuperclass();
//            }
//            if (type instanceof ParameterizedType) {
//                Type[] args = ((ParameterizedType) type).getActualTypeArguments();
//                if (args[0] instanceof Class) {
//                    return args[0];
//                } else if (args[0] instanceof ParameterizedType) {
//                    return args[0];
//                }
//            }
//            clazz = clazz.getSuperclass();
//        }
//        return null;
//    }

    /**
     * 获取类声明的泛型
     *
     * @param realize 目标类
     * @param define  定义泛型的 class
     * @return 返回泛型对应type {@link Type}, 获取失败返回null.
     */
    @Nullable
    public static Type getGenericType(final Class realize, final Class define) {
        Type type = null;
        if (realize != Object.class) {
            if (define.isAssignableFrom(realize)) {
                Class[] interfaces = realize.getInterfaces();
                // 1、获取接口声明泛型
                for (Class _interface : interfaces) {
                    if (define.isAssignableFrom(_interface)) {
                        Log.e("TAG", "获取接口: " + _interface);
                        type = getGenericType(_interface, define);
                        Log.e("TAG", "接口: " + type);
                        break;
                    }
                }
                // 2、获取父类声明泛型
                if (type == null) {
                    Class superClass = realize.getSuperclass();
                    if (superClass != null && define.isAssignableFrom(superClass)) {
                        Log.e("TAG", "获取父类: " + superClass);
                        type = getGenericType(superClass, define);
                        Log.e("TAG", "父类: " + type);
                    }
                }
                // 3、获取本身声明的泛型
                if (type == null) {
                    type = Generics.getCurrentGenericType(realize, define);
                }
            }
        }
        return type;
    }

    /**
     * 获取类当前泛型
     *
     * @param realize  参数化类型的类 class
     * @param define 定义泛型的 class
     * @return 实际类型参数（泛型）
     */
    @Nullable
    public static Type getCurrentGenericType(final Class realize, final Class define) {
        if (realize != Object.class) {
            Type type = null;
            Type[] generics = realize.getGenericInterfaces();
            for (Type generic : generics) {
                if (generic instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) generic).getRawType();
                    if (define == null ||
                            (rawType instanceof Class && define.isAssignableFrom((Class<?>) rawType))) {
                        type = generic;
                        Log.e("TAG", "当前接口: " + rawType + " - " + type);
                        break;
                    }
                }
            }

            if (type == null)
                if (define == null || define.isAssignableFrom(realize)) {
                    // 类/抽象类上的实际类型参数
                    type = realize.getGenericSuperclass();
                    Log.e("TAG", "当前类: " + realize + " - " + type);
                }

            if (type instanceof ParameterizedType) {
                Type[] args = ((ParameterizedType) type).getActualTypeArguments();
                if (args[0] instanceof Class) {
                    return args[0];
                } else if (args[0] instanceof ParameterizedType) {
                    return args[0];
                }
            }
        }
        return null;
    }

}
