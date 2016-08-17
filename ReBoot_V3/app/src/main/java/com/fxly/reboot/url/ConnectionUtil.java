package com.fxly.reboot.url;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fxly.reboot.R;

/**
 * Created by Lambert Liu on 2016-08-16.
 */
public class ConnectionUtil {
    /**
     * 检查网络是否可用
     *
     * @param context 应用程序的上下文对象
     * @return
     */
 /*
     * 判断网络连接是否已开
     * 2012-08-20
     *true 已打开  false 未打开
     * */
    public static boolean isConn(Context context) {
        boolean bisConnFlag = false;
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if (network != null) {
            bisConnFlag = conManager.getActiveNetworkInfo().isAvailable();
        }
        return bisConnFlag;
    }

    /*
       * 打开设置网络界面
       * */
    public static void setNetworkMethod(final Context context) {
        //提示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.network_setting).setMessage(R.string.network_notwork).setPositiveButton(R.string.action_settings, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent = null;
                //判断手机系统的版本  即API大于10 就是3.0或以上版本
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                context.startActivity(intent);
            }
        }).setNegativeButton(R.string.menu_canncel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                System.exit(0);


            }
        }).show();
    }

}
