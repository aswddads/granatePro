package com.tjun.www.granatepro.component;

import android.content.Context;

import com.tjun.www.granatepro.MyApp;
import com.tjun.www.granatepro.module.ApplicationModule;
import com.tjun.www.granatepro.module.HttpModule;
import com.tjun.www.granatepro.net.JanDanApi;
import com.tjun.www.granatepro.net.NewsApi;

import dagger.Component;

/**
 * create by tjun
 */
@Component(modules = {ApplicationModule.class,HttpModule.class})
public interface ApplicationComponent {

    MyApp getApplication();

    NewsApi getNetEaseApi();

    JanDanApi getJanDanApi();

    Context getContext();

}
