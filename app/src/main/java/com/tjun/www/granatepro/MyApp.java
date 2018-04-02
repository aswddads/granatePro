package com.tjun.www.granatepro;


import com.tjun.www.granatepro.bean.Constants;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.component.DaggerApplicationComponent;
import com.tjun.www.granatepro.module.ApplicationModule;
import com.tjun.www.granatepro.module.HttpModule;
import com.tjun.www.granatepro.utils.ContextUtils;
import com.tjun.www.granatepro.utils.SpUtils;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;
import cn.bmob.v3.Bmob;

/**
 * Created by tanjun on 2018/3/07.
 */
public class MyApp extends LitePalApplication {

    private ApplicationComponent mApplicationComponent;

    private static MyApp sMyApp;

    public static int width = 0;

    public static int height = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        SpUtils.putBoolean(this,Constants.IS_LODING,false);

        sMyApp = this;
        BGASwipeBackManager.getInstance().init(this);
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();
        LitePal.initialize(this);
        //初始化bmob
        Bmob.initialize(this, Constants.BOMB_APP_ID);

        width = ContextUtils.getSreenWidth(MyApp.getContext());
        height = ContextUtils.getSreenHeight(MyApp.getContext());
    }

    public static MyApp getInstance() {
        return sMyApp;
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

}
