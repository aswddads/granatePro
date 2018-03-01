package com.tjun.www.granatepro.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tanjun on 2018/3/1.
 * gson utils
 */

public class GsonUtils {
    /**
     * json 转化为实体类
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getModelFromJson(String json,Class<T> tClass) {
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(json,tClass);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 获取list实体列表
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> getListModelFromJson(String json,Class<T> tClass){
        Gson gson = new Gson();
        ArrayList<T> list = new ArrayList<T>();
        try {
            list = gson.fromJson(json,new TypeToken<ArrayList<T>>(){}.getType());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取map list
     * @param json
     * @param <T>
     * @return
     */
    public static <T> List<Map<String,Object>> getListMapFromJson(String json) {
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        Gson gson = new Gson();
        try {
            list = gson.fromJson(json,
                    new TypeToken<List<Map<String, Object>>>() {
                    }.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * json到实体
     * @param json
     * @param c
     * @param <T>
     * @return
     */
    public static <T extends Object> T jsonToBean(String json,Class<?> c) {
        Object o = null;
        Gson gson = new Gson();
        if (gson != null){
            if (!TextUtils.isEmpty(json)) {
                o = gson.fromJson(json,c);
            }
        }
        return (T) o;
    }
}
