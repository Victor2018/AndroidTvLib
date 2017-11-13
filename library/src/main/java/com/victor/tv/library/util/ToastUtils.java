package com.victor.tv.library.util;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.widget.Toast;

/**
 * 吐司工具类
 */

public class ToastUtils {

    /**
     * 调试模式下可显示
     *
     * @param msg
     */
    public static void showDebug(Context context, String msg) {
        if (Constant.MODEL_DEBUG) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 调试模式下可显示
     *
     * @param resId
     */
    public static void showDebug(Context context,@IntegerRes int resId) {
        if (Constant.MODEL_DEBUG) {
            final String text = ResUtils.getStringRes(context,resId);
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 短暂显示
     *
     * @param msg
     */
    public static void showShort(Context context,CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短暂显示
     *
     * @param resId
     */
    public static void showShort(Context context,int resId) {
        final String text = ResUtils.getStringRes(context,resId);
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示
     *
     * @param msg
     */
    public static void showLong(Context context,CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 短暂显示
     *
     * @param resId
     */
    public static void showLong(Context context,int resId) {
        final String text = ResUtils.getStringRes(context,resId);
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

}
