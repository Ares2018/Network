package com.aliya.sample.network.base;

import com.core.network.callback.ApiCallback;
import com.core.network.callback.ApiProgressCallback;

/**
 * Api progress callback joint api callback
 *
 * @author a_liYa
 * @date 2019/3/28 14:10.
 */
public interface ApiJointCallback<EntityType> extends ApiCallback<EntityType>, ApiProgressCallback {

}
