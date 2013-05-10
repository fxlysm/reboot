package com.liuyong.ginwave;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.widget.TextView;



public class MainActivity extends Activity {

  private Handler mHandler = new Handler();

	   public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
        TextView tv = new TextView(this);
	        tv.setText("Hello!\n\nNOTICE:\nThis softwave Settings  to reboot after 30 seconds \n\nAPI:android2.3 above\n\nCopyright by liuyong 2012\n\nEmail:liuyong@ginwave.com\n\nTel:13538189575");

	        setContentView(tv); 
	        
	        
	        mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent intent = new Intent(Intent.ACTION_REBOOT); 
					intent.setAction(Intent.ACTION_REBOOT); 
					intent.putExtra("nowait", 1); 
					intent.putExtra("interval", 1); 
					intent.putExtra("window", 0); 
					sendBroadcast(intent); 
				}
			}, 60000); //设置开机后30秒中启动重启服务
	        

       	        


	        
	    }
	
}
