package com.tjun.www.granatepro.component;

import android.content.Context;

import com.tjun.www.granatepro.model.ApplicationModule;
import com.tjun.www.granatepro.model.HttpModule;
import com.tjun.www.granatepro.net.NewsApi;

import dagger.Component;

/**
 * Created by tanjun on 2018/3/1.
 */

@Component(modules = {ApplicationModule.class,HttpModule.class})
public interface ApplicationComponent {
    Context getContext();
    NewsApi getNetEaseApi();

}
