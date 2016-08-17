package com.fxly.reboot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Lambert Liu on 2016-08-15.
 */
public class AboutDialog extends AlertDialog{
    public AboutDialog(Context context) {
        super(context);
        final View view = getLayoutInflater().inflate(R.layout.about_layout,
                null);
        setButton(context.getText(R.string.menu_close), (OnClickListener) null);
        setIcon(R.drawable.ic_menu_user);
//        setTitle();
        this.setTitle(R.string.menu_guide);

        setView(view);
//       R.string.app_name+" V 1.0"
//        this.getString(R.string.app_my_name)
    }


//    /**
//     * 获取版本号
//     * @return 当前应用的版本号
//     */
//    public String getVersion() {
//        try {
//            PackageManager manager = this.getPackageManager();
//            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
//            String version = info.versionName;
//            return this.getString(R.string.version_name) + version;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return this.getString(R.string.can_not_find_version_name);
//        }
//    }
}
