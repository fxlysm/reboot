package com.fxly.reboot;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.fxly.reboot.adapter.StaticFunction;
import com.fxly.reboot.view.ViewTargets;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        remaining_count_running = (TextView) findViewById(R.id.remaining_count_running);
        setttings_count_num= (TextView) findViewById(R.id.setttings_count_num);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        frist_luncher();


        showguide();


//        KeyguardManager mKeyGuardManager = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
//        KeyguardManager.KeyguardLock mLock = mKeyGuardManager.newKeyguardLock("com.fxly.reboot.MainActivity");
//        mLock.disableKeyguard();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.main, menu);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_reset) {
//
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            // Handle the camera action
            startActivity(new Intent().setClass(MainActivity.this,SettingsActivity.class));
        } else if (id == R.id.nav_language) {
            startActivity(new Intent().setClass(MainActivity.this,LanguageSwitchActivity.class));
        }
//        else if (id == R.id.nav_share) {
//            Intent intent=new Intent(Intent.ACTION_SEND);
//            intent.setType("image/*");
//            intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
//            intent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app (分享自"+R.string.app_name+")");
//
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(Intent.createChooser(intent, getTitle()));
//        }
        else if (id == R.id.nav_about) {
            startActivity(new Intent().setClass(MainActivity.this,AboutActivity.class));
        }
// else if(id== R.id.action_reset){
//
//            SharedPreferences mysharedpreferences = getSharedPreferences("reboot_settings", Activity.MODE_PRIVATE);
//            SharedPreferences.Editor editor = mysharedpreferences.edit();
//            editor.putString("total_count", "100");
//            editor.commit();
//        }
        else if( id == R.id.nav_user_guide){
            new AboutDialog(this).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    protected void onStart() {
        reloadLanguageAction();
        super.onStart();
        SharedPreferences mysharedpreferences = getSharedPreferences("reboot_settings", Activity.MODE_PRIVATE);
        setttings_count_num.setText(this.getString(R.string.settings_count_number,Integer.parseInt(mysharedpreferences.getString("total_count", "100")) ));
        int remaining_count_num=mysharedpreferences.getInt("running_count", 100);
        remaining_count_running.setText(String.valueOf(remaining_count_num));
        if(remaining_count_num>0){
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
        if(remaining_count_num>0){
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
            if(remaining_count>0){
                editor.putInt("running_count", remaining_count);
            }else {
                editor.putInt("running_count", 0);
            }

            editor.commit();
            new AboutDialog(this).show();
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
        if(remaining_count>0){
            editor.putInt("running_count", remaining_count);
        }else {
            editor.putInt("running_count", 0);
        }

        if (first) {
            editor.putBoolean(FIRST_RUN, false);
        }
        // Commit the edits!
        editor.commit();
    }
    public void restart_to_running(){
        Dialog dialog=new AlertDialog.Builder(MainActivity.this)

                .setTitle(R.string.menu_warm)

                .setIcon(android.R.drawable.stat_sys_warning)
                //.setIcon(BIND_ADJUST_WITH_ACTIVITY)
                .setMessage(R.string.warm_message
                )
                .setPositiveButton(R.string.nemu_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //确定的话就表示退出，此时我们结束我们程序
                        //使用我们Activity提供的finish方法


                        dialog.dismiss();
                        startActivity(new Intent().setClass(MainActivity.this,SettingsActivity.class));
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        dialogInterface.dismiss();
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
        Locale locale = StaticFunction.getSystemLacate(MainActivity.this);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, null);
        getBaseContext().getResources().flushLayoutCache();

    }


    public void showguide(){
//        Target viewTarget = new ViewTarget(R.id.fab, this);
        new ShowcaseView.Builder(this)
//                .setTarget(viewTarget)
                .setTarget(new ViewTarget(R.id.fab, this))
                .withMaterialShowcase()
                .setContentTitle("Notice")
                .setStyle(R.style.CustomShowcaseTheme_menu)
                .setContentText("This Button is reset and restart to runing")
                .singleShot(22)

                .build();



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        try {
//            ViewTarget navigationButtonViewTarget = ViewTargets.navigationButtonViewTarget(toolbar);
//            new ShowcaseView.Builder(this)
//                    .withMaterialShowcase()
//                    .setTarget(navigationButtonViewTarget)
//                    .setStyle(R.style.CustomShowcaseTheme_menu)
//                    .setContentText("Here's how to menus items on a toolbar")
//                    .setShowcaseEventListener(new OnShowcaseEventListener() {
//                        @Override
//                        public void onShowcaseViewHide(ShowcaseView showcaseView) {
//
//                        }
//
//                        @Override
//                        public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
//                            showcaseView.hide();
//                        }
//
//                        @Override
//                        public void onShowcaseViewShow(ShowcaseView showcaseView) {
//
//                        }
//
//                        @Override
//                        public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {
//
//                        }
//                    })
//
//                    .build()
//                    .show();
//        } catch (ViewTargets.MissingViewException e) {
//            e.printStackTrace();
//        }
    }
}

