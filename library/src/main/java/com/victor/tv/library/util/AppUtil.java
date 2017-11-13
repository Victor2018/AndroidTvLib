package com.victor.tv.library.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by victor on 2017/10/16.
 */

public class AppUtil {
    // 应用版本
    public static int getAppVersionCode(Context context) {
        String packageName = context.getPackageName();
        try {
            int versionCode = context.getPackageManager().getPackageInfo(
                    packageName, 0).versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("System fault!!!", e);
        }
    }
    public static String getAppVersionName(Context context) {
        String packageName = context.getPackageName();
        try {
            String versionName = context.getPackageManager().getPackageInfo(
                    packageName, 0).versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("System fault!!!", e);
        }
    }

    /**
     * 获取APP名称
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        String appName ="";
        try {
            PackageManager packageManager = context.getPackageManager();

            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            appName = packageManager.getApplicationLabel(applicationInfo).toString();
            return appName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("System fault!!!", e);
        }
    }

    public static boolean isAppExist(Context context,String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> applicationInfos = packageManager.getInstalledApplications(0);
        for (ApplicationInfo info : applicationInfos) {
            if (TextUtils.equals(info.packageName, packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取SDK版本
     */
    public static int getSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
        }
        return version;
    }

}
