package com.tjun.www.granatepro.model;

import com.tjun.www.granatepro.MyApp;

import java.io.File;

import dagger.Module;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by tanjun on 2018/3/12.
 */

@Module
public class HttpModule {

    OkHttpClient.Builder provideOkhttpClient() {
        Cache cache = new Cache(new File(MyApp.getContext().getCacheDir(),"HttpCache"),1024 * 1024 * 10);
        return new OkHttpClient().newBuilder().cache(cache)
                .retryOnConnectionFailure(true)
                .
    }
}
