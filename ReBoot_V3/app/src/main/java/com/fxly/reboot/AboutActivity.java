package com.fxly.reboot;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AboutActivity extends AppCompatActivity {
    private TextView app_verison;
    private LinearLayout about_email,about_qq,about_github;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        app_verison= (TextView) findViewById(R.id.app_verison);
        app_verison.setText(getVersion());

         about_email = (LinearLayout) findViewById(R.id.about_email);
         about_qq = (LinearLayout) findViewById(R.id.about_qq);
        about_github = (LinearLayout) findViewById(R.id.about_github);
        about_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMailByIntent();
            }
        });
        about_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAvilible(AboutActivity.this, "com.tencent.mobileqq")){

                    String url="mqqwpa://im/chat?chat_type=wpa&uin=814380399";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }else {
                    Toast.makeText(AboutActivity.this, R.string.qq_app_check, Toast.LENGTH_SHORT).show();
                }
            }
        });


        about_github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AboutActivity.this, MyWebView.class);
                i.putExtra("web", "Reboot");
                startActivity(i);
            }
        });
    }


    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return this.getString(R.string.version_name) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.can_not_find_version_name);
        }
    }


    private boolean isAvilible(Context context, String packageName )
    {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for ( int i = 0; i < pinfo.size(); i++ )
        {
            if(pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }



    // you need config the mail app in your android moble first,and the mail will send by the mail app. and there are one big bug:
    //you can't send the mail Silently and you need to click the send button
    public int sendMailByIntent() {
        String[] reciver = new String[] { "lambert.liu@nf-technology.com" };
        String[] mySbuject = new String[] { getString(R.string.app_name)+" V "+getVersion() };
        String mybody = R.string.app_name+" V "+getVersion()+"\n";
        Intent myIntent = new Intent(android.content.Intent.ACTION_SEND);
        myIntent.setType("plain/text");
        myIntent.putExtra(android.content.Intent.EXTRA_EMAIL, reciver);
        myIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mySbuject);
        myIntent.putExtra(android.content.Intent.EXTRA_TEXT, mybody);
        startActivity(Intent.createChooser(myIntent, getString(R.string.app_name)+getVersion() +getString(R.string.sent_to)));

        return 1;

    }
}
