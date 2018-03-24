package com.tjun.www.granatepro.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tjun.www.granatepro.bean.Constants;

/**
 * Created by tanjun on 2018/3/15.
 */

public class SpUtils {
    private static SharedPreferences sp;
    private static final String SHARED_PRE_NAME = "shared_preferences_asw_tan";

    private static SharedPreferences getSp(Context context) {
        if (sp ==null) {
            sp = context.getSharedPreferences(SHARED_PRE_NAME,Context.MODE_PRIVATE);
        }
        return sp;
    }

    /**
     * Boolean类型sp操作
     * @param context
     * @param key
     * @param value
     */
    public static void putBoolean(Context context,String key,boolean value){
        getSp(context).edit().putBoolean(key,value).apply();
    }

    public static boolean getBoolean(Context context,String key,boolean defValue){
        return getSp(context).getBoolean(key,defValue);
    }

    /**
     * String类型sp操作
     * @param context
     * @param key
     * @param value
     */

    public static void putString(Context context,String key,String value){
        getSp(context).edit().putString(key,value).apply();
    }

    public static String getString(Context context,String key,String defValue){
        return getSp(context).getString(key,defValue);
    }

    /**
     * Int类型sp操作
     * @param context
     * @param key
     * @param value
     */

    public static void putInt(Context context,String key,int value){
        getSp(context).edit().putInt(key,value).apply();
    }

    public static int getInt(Context context,String key,int defValue){
        return getSp(context).getInt(key,defValue);
    }

    /**
     * 删除指定数据
     * @param context
     * @param key
     */
    public static void delete(Context context,String key) {
        getSp(context).edit().remove(key).apply();
    }

}
