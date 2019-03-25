package com.core.network.api;

/**
 * Api 获取泛型携带者 - 接口
 *
 * @author a_liYa
 * @date 2019/3/20 09:53.
 */
public interface ApiGenericCarrier {

    /**
     * 泛型实例化声明 class
     *
     * @return
     */
    Class getGenericRealize();

    /**
     * 泛型定义接口 class
     *
     * @return class
     */
    Class getGenericDefine();

}
