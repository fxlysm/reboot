package com.fxly.restart;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class SettingsActivity extends AppCompatActivity {

    private SeekBar timeline_hor;
    private EditText total_reboot_count;
    private TextView time_hor;
    private  LinearLayout settings_language,about_boke;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        total_reboot_count = (EditText) findViewById(R.id.total_reboot_count);
        about_boke=(LinearLayout)findViewById(R.id.about_us);
        settings_language =(LinearLayout)findViewById(R.id.settings_language);


        time_hor = (TextView) findViewById(R.id.time_hor);
        timeline_hor = (SeekBar) findViewById(R.id.timeline_hor);

        timeline_hor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                float size = (float)i/4;
                DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
                String filesize = df.format(size);//返回的是String类型的
                if(filesize.equals("0.25")){time_hor.setText("15 S");}
                else if(filesize.equals("0.50")){time_hor.setText("30 S");}
                else if(filesize.equals("0.75")){time_hor.setText("45 S");}
                else if(filesize.equals("1.00")){time_hor.setText("60 S");}
                else if(filesize.equals("1.25")){time_hor.setText("75 S");}
                else if(filesize.equals("1.50")){time_hor.setText("90 S");}
                else if(filesize.equals("1.75")){time_hor.setText("1 Min 45 S");}
                else if(filesize.equals("2.00")){time_hor.setText("2 Min ");}
                else if(filesize.equals("2.25")){time_hor.setText("2 Min 15 S");}
                else if(filesize.equals("2.50")){time_hor.setText("2 Min 30 S");}
                else if(filesize.equals("2.75")){time_hor.setText("2 Min 45 S");}
                else if(filesize.equals("3.00")){time_hor.setText("3 Min ");}
                else if(filesize.equals("3.25")){time_hor.setText("3 Min 30 S");}
                else if(filesize.equals("3.50")){time_hor.setText("3 Min 30 S");}
                else if(filesize.equals("3.75")){time_hor.setText("3 Min 45 S");}
                else if(filesize.equals("4.00")){time_hor.setText("4 Min");}
                else if(filesize.equals("4.25")){time_hor.setText("4 Min 15 S");}
                else if(filesize.equals("4.50")){time_hor.setText("4 Min 30 S");}
                else if(filesize.equals("4.75")){time_hor.setText("4 Min 45 S");}
                else if(filesize.equals("5.00")){time_hor.setText("5 Min ");}
                else {
                    time_hor.setText(filesize);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int interval_min = seekBar.getProgress();
                SharedPreferences mysharedpreferences = getSharedPreferences("reboot_settings", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mysharedpreferences.edit();
                editor.putInt("control_min", interval_min);
                editor.commit();
            }
        });

        settings_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(SettingsActivity.this,LanguageSwitchActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences mysharedpreferences = getSharedPreferences("reboot_settings", Activity.MODE_PRIVATE);
        int getinterview_hor = mysharedpreferences.getInt("control_min", 4);
        time_hor.setText(String.valueOf(getinterview_hor));
        timeline_hor.setProgress(getinterview_hor);

        total_reboot_count.setText(mysharedpreferences.getString("total_count","100"));
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();


        SharedPreferences mysharedpreferences = getSharedPreferences("reboot_settings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mysharedpreferences.edit();
        editor.putString("total_count", total_reboot_count.getText().toString());
        editor.putInt("running_count", Integer.valueOf(total_reboot_count.getText().toString()).intValue());
        editor.commit();
    }
}
