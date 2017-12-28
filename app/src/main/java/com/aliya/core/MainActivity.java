package com.aliya.core;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aliya.core.network.callback.ApiCallback;
import com.aliya.core.task.QRCodeTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new QRCodeTask(new ApiCallback<String>() {
            @Override
            public void onCancel() {
                Log.e("TAG", "onCancel ");
            }

            @Override
            public void onError(String errMsg, int errCode) {
                Log.e("TAG", "onError " + errMsg + " - " + errCode);
            }

            @Override
            public void onSuccess(String data) {
                Log.e("TAG", "onSuccess " + data);
            }
        }).exe();

    }
}
