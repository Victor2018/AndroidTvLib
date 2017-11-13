package com.victor.tv.library.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by yf04 on 2017/10/11.
 */

public class KeyBoardUtil {
    /**
     * 显示系统软键盘
     */
    public static void showKeyBoard (Context context,View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏系统键盘
     * @param view
     */
    public static void hideKeyBoard (Context context,View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
