package com.aliya.core;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aliya.core.entity.DataApi;
import com.aliya.core.entity.LoginEntity;
import com.aliya.core.entity.MetaCategoryEntity;
import com.aliya.core.entity.QREntity;
import com.aliya.core.entity.ReturnBean;
import com.aliya.core.entity.UserInfoEntity;
import com.aliya.core.task.ApiInitTask;
import com.aliya.core.task.MobileTest;
import com.aliya.core.task.MobileTestTask;
import com.aliya.core.task.QRCodeTask;
import com.core.network.callback.ApiCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    String token = "AAAesgAHdGVzdGFwcAAodGVzdGFwcCw4MkY1NThFNzE1RTE5QUE3RUU2QjhCN0UxQTFFODdDMQAAAC8wLQIVAIG0oB0ZM8AFf_v9pBiLqiJ7YqoAAhQUI4Xn7i3I7GyiqSBv-7xS5icmLA..";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        long millis = SystemClock.uptimeMillis();
//
//        new QRCodeTask(new ApiCallback<QREntity>() {
//            @Override
//            public void onCancel() {
//                Log.e("TAG", "onCancel ");
//            }
//
//            @Override
//            public void onError(String errMsg, int errCode) {
//                Log.e("TAG", "onError " + errMsg + " - " + errCode);
//            }
//
//            @Override
//            public void onSuccess(QREntity data) {
//                Log.e("TAG", "onSuccess " + data);
//            }
//        }).setTag(this).exe();
//
//        new ApiInitTask(new ApiCallback<ReturnBean<DataApi>>() {
//            @Override
//            public void onCancel() {
//                Log.e("TAG", "onCancel ");
//            }
//
//            @Override
//            public void onError(String errMsg, int errCode) {
//                Log.e("TAG", "onError " + errMsg + " - " + errCode);
//            }
//
//            @Override
//            public void onSuccess(ReturnBean<DataApi> data) {
//                Log.e("TAG", "onSuccess " + data);
//            }
//        }).exe();
//
//        Log.e("TAG", "task 耗时 " + (SystemClock.uptimeMillis() - millis));

        new MobileTest(new ApiCallback<LoginEntity>() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onError(String errMsg, int errCode) {
                Log.e("TGA", errMsg + "," + errCode);

            }

            @Override
            public void onSuccess(LoginEntity data) {
                Log.e("TGA", data.getToken());
            }
        }).exe();


        new MobileTestTask(new ApiCallback<List<MetaCategoryEntity>>() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onError(String errMsg, int errCode) {

            }

            @Override
            public void onSuccess(List<MetaCategoryEntity> data) {
                Log.e("TGA", data.toString());
            }
        }).exe();
    }

}
