package com.fxly.reboot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.text.DecimalFormat;

public class SettingsActivity extends AppCompatActivity {
    private SeekBar timeline_hor;
    private EditText total_reboot_count;
    private TextView time_hor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        total_reboot_count = (EditText) findViewById(R.id.total_reboot_count);


        time_hor = (TextView) findViewById(R.id.time_hor);
        timeline_hor = (SeekBar) findViewById(R.id.timeline_hor);

        timeline_hor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                float size = (float) i / 4;
                DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
                String filesize = df.format(size);//返回的是String类型的

                if (filesize.equals("0.25")) {
                    time_hor.setText("15"+getString(R.string.time_seconds));
                } else if (filesize.equals("0.50")) {
                    time_hor.setText("30"+getString(R.string.time_seconds));
                } else if (filesize.equals("0.75")) {
                    time_hor.setText("45"+getString(R.string.time_seconds));
                } else if (filesize.equals("1.00")) {
                    time_hor.setText("60"+getString(R.string.time_seconds));
                } else if (filesize.equals("1.25")) {
                    time_hor.setText("75"+getString(R.string.time_seconds));
                } else if (filesize.equals("1.50")) {
                    time_hor.setText("90"+getString(R.string.time_seconds));
                } else if (filesize.equals("1.75")) {
                    time_hor.setText("1"+getString(R.string.time_min)+" 45"+getString(R.string.time_seconds));
                } else if (filesize.equals("2.00")) {
                    time_hor.setText("2"+getString(R.string.time_min));
                } else if (filesize.equals("2.25")) {
                    time_hor.setText("2"+getString(R.string.time_min)+" 15"+getString(R.string.time_seconds));
                } else if (filesize.equals("2.50")) {
                    time_hor.setText("2"+getString(R.string.time_min)+"30"+getString(R.string.time_seconds));
                } else if (filesize.equals("2.75")) {
                    time_hor.setText("2 Min 45"+getString(R.string.time_seconds));
                } else if (filesize.equals("3.00")) {
                    time_hor.setText("3 "+getString(R.string.time_min));
                } else if (filesize.equals("3.25")) {
                    time_hor.setText("3"+getString(R.string.time_min)+" 15"+getString(R.string.time_seconds));
                } else if (filesize.equals("3.50")) {
                    time_hor.setText("3 "+getString(R.string.time_min)+" 30"+getString(R.string.time_seconds));
                } else if (filesize.equals("3.75")) {
                    time_hor.setText("3 Min 45"+getString(R.string.time_seconds));
                } else if (filesize.equals("4.00")) {
                    time_hor.setText("4 "+getString(R.string.time_min));
                } else if (filesize.equals("4.25")) {
                    time_hor.setText("4 "+getString(R.string.time_min)+" 15"+getString(R.string.time_seconds));
                } else if (filesize.equals("4.50")) {
                    time_hor.setText("4 "+getString(R.string.time_min)+" 30"+getString(R.string.time_seconds));
                } else if (filesize.equals("4.75")) {
                    time_hor.setText("4 "+getString(R.string.time_min)+" 45"+getString(R.string.time_seconds));
                } else if (filesize.equals("5.00")) {
                    time_hor.setText("5"+getString(R.string.time_min));
                } else if(filesize.equals("0.00")){
                    timeline_hor.setProgress(1);
                }

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


    }


    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences mysharedpreferences = getSharedPreferences("reboot_settings", Activity.MODE_PRIVATE);
        int getinterview_hor = mysharedpreferences.getInt("control_min", 4);
        time_hor.setText(String.valueOf(getinterview_hor));
        timeline_hor.setProgress(getinterview_hor);

        total_reboot_count.setText(mysharedpreferences.getString("total_count", "100"));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ic_menu_save) {
            SharedPreferences mysharedpreferences = getSharedPreferences("reboot_settings", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = mysharedpreferences.edit();
            editor.putString("total_count", total_reboot_count.getText().toString());
            editor.putInt("running_count", Integer.valueOf(total_reboot_count.getText().toString()).intValue());
            editor.commit();
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }





}
