#reboot循环重启
在做测试时，手动开关机是一个繁琐的过程，那么为了方便测试人员针对ANDROID设备进行开关机测试

重启核心代码：

Intent intent = new Intent(Intent.ACTION_REBOOT); 


					intent.setAction(Intent.ACTION_REBOOT); 
					
					intent.putExtra("nowait", 1); 
					
					intent.putExtra("interval", 1); 
					
					intent.putExtra("window", 0); 
					
					sendBroadcast(intent); 
					
					
注册一广播并设置开机启动android.intent.action.BOOT_COMPLETED

最后加上系统权限
  <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" ></uses-permission>
    <uses-permission android:name="android.permission.REBOOT" ></uses-permission>


自动化测试不是问题，方法总比困难多！
