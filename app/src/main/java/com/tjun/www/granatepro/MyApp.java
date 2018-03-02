package com.tjun.www.granatepro;

import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.utils.ContextUtils;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created by tanjun on 2018/2/27.
 */

public class MyApp extends LitePalApplication{

    private static MyApp mApp;

    public static int width = 0;

    public static int height = 0;

    private ApplicationComponent mApplicationComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        LitePal.initialize(this);
        width = ContextUtils.getScreenWidth(MyApp.getContext());
        height = ContextUtils.getScreenHeight(MyApp.getContext());
    }

    public static MyApp getInstance(){
        return mApp;
    }
}
