package com.victor.tv.library.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;


/**
 * 获取设备的信息
 */

public class DeviceUtils {

    private static DisplayMetrics mDisplayMetrics;

    /**
     * 获取设备的宽和高
     *
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        if (mDisplayMetrics == null) {
            mDisplayMetrics = context.getResources().getDisplayMetrics();
        }
        return mDisplayMetrics;
    }

    public static int getDisplayWidth(Context context) {
        if (mDisplayMetrics == null) {
            mDisplayMetrics = context.getResources().getDisplayMetrics();
        }
        return mDisplayMetrics.widthPixels;
    }

    public static int getDisplayHeight(Context context) {
        if (mDisplayMetrics == null) {
            mDisplayMetrics = context.getResources().getDisplayMetrics();
        }
        return mDisplayMetrics.heightPixels;
    }

    @SuppressLint("NewApi")
    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 物理地址加device id
     *
     * @return
     */
    public static String getUDID(Context context) {
        try {
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            return CryptoUtils.MD5(device_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
