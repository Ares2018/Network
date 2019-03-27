package com.aliya.sample;

import com.aliya.sample.entity.QREntity;
import com.core.network.callback.ApiCallback;

import java.io.Serializable;

/**
 * LoadMore TODO
 *
 * @author a_liYa
 * @date 2019/3/19 16:27.
 */
public class LoadMore implements ApiCallbackImpl, Serializable {

    public LoadMore() {
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(String errMsg, int errCode) {

    }

    @Override
    public void onSuccess(QREntity data) {

    }

}

interface ApiCallbackImpl extends ApiCallback2<QREntity> {

}

interface ApiCallback2<T> extends ApiCallback<T> {

}


