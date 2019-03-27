package com.aliya.sample;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aliya.sample.entity.DataApi;
import com.aliya.sample.entity.QREntity;
import com.aliya.sample.entity.ReturnBean;
import com.aliya.sample.task.ApiInitTask;
import com.aliya.sample.task.QRCodeTask;
import com.core.network.callback.ApiCallback;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long millis = SystemClock.uptimeMillis();
        new QRCodeTask(new ApiCallback<QREntity>() {
            @Override
            public void onCancel() {
                Log.e("TAG", "onCancel ");
            }

            @Override
            public void onError(String errMsg, int errCode) {
                Log.e("TAG", "onError " + errMsg + " - " + errCode);
            }

            @Override
            public void onSuccess(QREntity data) {
                Log.e("TAG", "onSuccess " + data);
            }
        }).setTag(this).exe();
        Log.e("TAG", "task 耗时 " + (SystemClock.uptimeMillis() - millis));
        new ApiInitTask(new ApiCallback<ReturnBean<DataApi>>() {
            @Override
            public void onCancel() {
                Log.e("TAG", "onCancel ");
            }

            @Override
            public void onError(String errMsg, int errCode) {
                Log.e("TAG", "onError " + errMsg + " - " + errCode);
            }

            @Override
            public void onSuccess(ReturnBean<DataApi> data) {
                Log.e("TAG", "onSuccess " + data);
            }
        }).exe();

        Log.e("TAG", "task 耗时 " + (SystemClock.uptimeMillis() - millis));
    }

}
