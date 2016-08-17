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

android.permission.RECEIVE_BOOT_COMPLETED

android.permission.REBOOT
 


自动化测试不是问题，方法总比困难多！


##更新V3--2016-08-17 

效果图：
![Screenshot](https://github.com/fxlysm/reboot/blob/master/picture/reboot_r3.gif)

此版使用侧滑菜单UI布局，自定义重启次数及重启时间间隔。

使用Libs

    compile 'com.github.amlcurran.showcaseview:library:5.4.3'
    
1.[Showcaseview](https://github.com/amlcurran/ShowcaseView)
