package com.fxly.reboot;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.os.Handler;
import java.util.List;


public class MainWellcome extends AppCompatActivity {
    private  String TAG="louis_log";
    private Handler mHandler = new Handler();
    private static final int REQUEST_CODE_PERMISSIONS_DUO = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wellcome);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainWellcome.this,MainActivity.class));
                MainWellcome.this.finish();
            }
        }, 2000); //设置开机后2秒中启动重启服务


    }


}
