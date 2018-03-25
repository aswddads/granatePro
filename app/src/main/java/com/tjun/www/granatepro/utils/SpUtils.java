package com.tjun.www.granatepro.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import com.tjun.www.granatepro.bean.Constants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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


    public static void putImageToSp(Context context, ImageView imageView) {
        //保存
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //bitmap压缩成字节数组输出
        ByteArrayOutputStream byStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
        //利用base64将字节数组输出流转化为String
        byte[] byteArray = byStream.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArray,Base64.DEFAULT));
        SpUtils.putString(context,Constants.IMAGE_TITLE,imageString);
    }

    public static void getImageFromSp(Context context, ImageView imageView){
        String imageString = SpUtils.getString(context,Constants.IMAGE_TITLE,"");
        if (!imageString.equals("")) {
            byte[] byteArray = Base64.decode(imageString,Base64.DEFAULT);
            ByteArrayInputStream byInputStream = new ByteArrayInputStream(byteArray);
            Bitmap bitmap = BitmapFactory.decodeStream(byInputStream);
            imageView.setImageBitmap(bitmap);
        }
    }
}
