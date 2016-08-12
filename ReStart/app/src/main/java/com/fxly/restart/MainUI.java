package com.fxly.restart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.os.Handler;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.fxly.restart.adapter.StaticFunction;

import java.util.Locale;

public class MainUI extends AppCompatActivity {
    private Handler mHandler = new Handler();
    private TextView remaining_count_running;


    private boolean FirstBoot = true;
    public static final String PREFS_NAME = "reboot_settings";
    public static final String FIRST_RUN = "first";
    private boolean first;


    private TextView setttings_count_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        remaining_count_running = (TextView) findViewById(R.id.remaining_count_running);
        setttings_count_num= (TextView) findViewById(R.id.setttings_count_num);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.start_test);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences mysharedpreferences = getSharedPreferences("reboot_settings", Activity.MODE_PRIVATE);
                int total_count_num = Integer.valueOf(mysharedpreferences.getString("total_count","100")).intValue();
                SharedPreferences.Editor editor = mysharedpreferences.edit();
                int remaining_count=total_count_num-1;

                editor.putInt("running_count", remaining_count);

                editor.commit();
                Snackbar.make(view, "ReSet the Reboot count:"+mysharedpreferences.getString("total_count","100"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                remaining_count_running.setText(mysharedpreferences.getString("total_count","100"));
            }
        });
        frist_luncher();

        reloadLanguageAction();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences mysharedpreferences = getSharedPreferences("reboot_settings", Activity.MODE_PRIVATE);
        setttings_count_num.setText(this.getString(R.string.settings_count_number,Integer.parseInt(mysharedpreferences.getString("total_count", "100")) ));
        int remaining_count_num=mysharedpreferences.getInt("running_count", 100);
        remaining_count_running.setText(String.valueOf(remaining_count_num+1));
        if(remaining_count_num>=0){
            reboot();
        }else {
            restart_to_running();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences mysharedpreferences = getSharedPreferences("reboot_settings", Activity.MODE_PRIVATE);

        int remaining_count_num=mysharedpreferences.getInt("running_count", 100);
        remaining_count_running.setText(String.valueOf(remaining_count_num));
        if(remaining_count_num>=0){
            reboot();
        }else {
            restart_to_running();
        }

    }

    //添加首次进入应用 2016-08-12

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SharedPreferences mysharedpreferences = getSharedPreferences("reboot_settings", Activity.MODE_PRIVATE);
//        int total_count_num = Integer.valueOf(mysharedpreferences.getString("total_count","100")).intValue();
//
//        int remaining_count=total_count_num-1;
//        SharedPreferences.Editor editor = mysharedpreferences.edit();
//        editor.putInt("remaining_count", remaining_count);
//        editor.commit();


    }

    public void reboot(){
        SharedPreferences mysharedpreferences = getSharedPreferences("reboot_settings", Activity.MODE_PRIVATE);
        int total_count_num = mysharedpreferences.getInt("control_min",4);
        int time_run=total_count_num*15000;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Intent.ACTION_REBOOT);
                intent.setAction(Intent.ACTION_REBOOT);
                intent.putExtra("nowait", 1);
                intent.putExtra("interval", 1);
                intent.putExtra("window", 0);
                sendBroadcast(intent);

//                  /*String str = "重启";
//                        try {
//                            str = runCmd("reboot", "/system/bin");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }*/
//                        /*Intent reboot = new Intent(Intent.ACTION_REBOOT);
//                        reboot.putExtra("nowait", 1);
//                        reboot.putExtra("interval", 1);
//                        reboot.putExtra("window", 0);
//                        sendBroadcast(reboot); */
//                PowerManager pManager=(PowerManager) getSystemService(Context.POWER_SERVICE);
//                pManager.reboot("重启");
            }
        }, time_run); //设置开机后30秒中启动重启服务

    }







    /****
     * 首次启动应用判断
     */
    public void frist_luncher(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        first = settings.getBoolean(FIRST_RUN, true);
        if (first) {

            int total_count_num = Integer.valueOf(settings.getString("total_count","100")).intValue();
            SharedPreferences.Editor editor = settings.edit();
            int remaining_count=total_count_num-1;
            editor.putInt("running_count", remaining_count);
            editor.commit();
        } else {


        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int count_num = settings.getInt("running_count",100);
        SharedPreferences.Editor editor = settings.edit();
        int remaining_count=count_num-1;
        editor.putInt("running_count", remaining_count);
        if (first) {
            editor.putBoolean(FIRST_RUN, false);
        }
        // Commit the edits!
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
           startActivity(new Intent().setClass(MainUI.this,SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }



    public void restart_to_running(){
        Dialog dialog=new AlertDialog.Builder(MainUI.this)

                .setTitle("温馨提示")

                .setIcon(android.R.drawable.ic_menu_more)
                //.setIcon(BIND_ADJUST_WITH_ACTIVITY)
                .setMessage("当前设置的次数0或已完成测试！" +
                        "\n"+"若需继续测试，请进入应用设置进行更改测试次数，返回主界面点击重新测试！"
                )
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //确定的话就表示退出，此时我们结束我们程序
                        //使用我们Activity提供的finish方法


                        dialog.dismiss();
                        startActivity(new Intent().setClass(MainUI.this,SettingsActivity.class));
                    }
                })
                .create();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER_HORIZONTAL);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.98f;
        window.setAttributes(lp);
        dialog.show();

    }

    private void reloadLanguageAction() {
        Locale locale = StaticFunction.getSystemLacate(MainUI.this);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, null);
        getBaseContext().getResources().flushLayoutCache();

    }

}
