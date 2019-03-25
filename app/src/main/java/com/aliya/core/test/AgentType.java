package com.aliya.core.test;

import com.core.network.api.ApiGenericCarrier;

/**
 * AgentType
 *
 * @author a_liYa
 * @date 2019/3/25 15:00.
 */
public class AgentType<T> implements IType<T>, ApiGenericCarrier {

    private I2<T> i2;

    public AgentType(I2<T> i2) {
        this.i2 = i2;
    }

    @Override
    public Class getGenericRealize() {
        return i2.getClass();
    }

    @Override
    public Class getGenericDefine() {
        return I2.class;
    }
}
