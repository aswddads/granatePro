package com.tjun.www.granatepro.module;

import android.content.Context;

import com.tjun.www.granatepro.MyApp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tanjun on 2018/3/07.
 */
@Module
public class ApplicationModule {

    private Context mContext;

    public ApplicationModule(Context context) {
        this.mContext = context;
    }

    @Provides
    MyApp provideApplication() {
        return (MyApp) mContext.getApplicationContext();
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
