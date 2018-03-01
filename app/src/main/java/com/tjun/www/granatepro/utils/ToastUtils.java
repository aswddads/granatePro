package com.tjun.www.granatepro.utils;

import android.content.Context;
import android.widget.Toast;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by tanjun on 2018/3/1.
 * Toast utils
 */

public class ToastUtils {
    /**
     * 短时间Toast
     * @param context
     * @param message
     */
    public static void showShort(Context context,CharSequence message) {
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间
     * @param context
     * @param message
     */
    public static void showLong(Context context,CharSequence message) {
            Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示时间
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context,CharSequence message,int duration) {
        Toast.makeText(context, message, duration).show();
    }
}
