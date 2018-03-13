package com.tjun.www.granatepro.net;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by tanjun on 2018/3/13.
 */

public class NewsApi {
    public static final String ACTION_DEFAULT = "default";
    public static final String ACTION_DOWN = "down";
    public static final String ACTION_UP = "up";

    @StringDef({ACTION_DEFAULT,ACTION_DOWN,ACTION_UP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Actions{

    }

    public static NewsApi sInstance;

    private NewsApiService mService;

    public NewsApi(NewsApiService newsApiService) {
        this.mService = newsApiService;
    }

    public static NewsApi getInstance(NewsApiService newsApiService) {
        if (sInstance == null)
            sInstance = new NewsApi(newsApiService);
        return sInstance;
    }
}
