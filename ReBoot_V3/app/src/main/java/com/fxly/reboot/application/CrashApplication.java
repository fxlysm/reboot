package com.fxly.reboot.application;

import android.app.Application;
import android.content.Intent;

import com.fxly.reboot.MainActivity;
import com.fxly.reboot.MainWellcome;

/**
 * Created by Lambert Liu on 2016-08-15.
 */
public class CrashApplication  extends Application {
    protected static CrashApplication instance;
    public void onCreate() {
        super.onCreate();
        instance = this;
        Thread.setDefaultUncaughtExceptionHandler(restartHandler); // 程序崩溃时触发线程  以下用来捕获程序崩溃异常
    }
    // 创建服务用于捕获崩溃异常
    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            restartApp();//发生崩溃异常时,重启应用
        }
    };
    public void restartApp(){
        Intent intent = new Intent(instance,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        instance.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
    }
}
