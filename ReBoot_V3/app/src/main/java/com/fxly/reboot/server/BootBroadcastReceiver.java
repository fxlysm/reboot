package com.fxly.reboot.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fxly.reboot.MainActivity;

/**
 * Created by Lambert Liu on 2016-08-15.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)){
            Intent sayHelloIntent=new Intent(context,MainActivity.class);
            sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(sayHelloIntent);
        }
    }

}
