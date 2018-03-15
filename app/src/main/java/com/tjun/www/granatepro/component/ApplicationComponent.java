package com.tjun.www.granatePro.component;

import android.content.Context;

import com.tjun.www.granatePro.MyApp;
import com.tjun.www.granatePro.module.ApplicationModule;
import com.tjun.www.granatePro.module.HttpModule;
import com.tjun.www.granatePro.net.JanDanApi;
import com.tjun.www.granatePro.net.NewsApi;

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
