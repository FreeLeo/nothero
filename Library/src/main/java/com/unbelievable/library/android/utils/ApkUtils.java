package com.unbelievable.library.android.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.unbelievable.library.nothero.R;

import java.io.File;

/**
 * Created by lizhen on 2017/8/1.
 */

public class ApkUtils {
    /**
     * 获取当前apk的versionCode
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return Integer.MAX_VALUE;
        }
    }

    /**
     * 获取当前apk的versionName
     * @param context
     * @return
     */
    public static int getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return Integer.MAX_VALUE;
        }
    }

    /**
     * 安装apk
     * @param context
     * @return
     */
    public static void install(Context context, String apkPath) {
        File file = new File(apkPath);
        if(!file.exists()){
            ToastUtils.toastL(context,context.getString(R.string.file_not_exist));
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
