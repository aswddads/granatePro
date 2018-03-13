package com.tjun.www.granatepro.model;

import com.tjun.www.granatepro.MyApp;
import com.tjun.www.granatepro.net.ApiConstans;
import com.tjun.www.granatepro.net.NewsApi;
import com.tjun.www.granatepro.net.NewsApiService;
import com.tjun.www.granatepro.net.RetrofitConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tanjun on 2018/3/12.
 */

@Module
public class HttpModule {

    @Provides
    OkHttpClient.Builder provideOkhttpClient() {
        Cache cache = new Cache(new File(MyApp.getContext().getCacheDir(),"HttpCache"),1024 * 1024 * 10);
        return new OkHttpClient().newBuilder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(RetrofitConfig.sLoggingInterceptor)
                .addInterceptor(RetrofitConfig.sRewriteCacheControlInterceptor)
                .addInterceptor(RetrofitConfig.sRewriteCacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS);
    }

    @Provides
    NewsApi provideNetEaseApis(OkHttpClient.Builder builder) {
        builder.addInterceptor(RetrofitConfig.sQueryParameterInterceptor);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build());

        return NewsApi.getInstance(retrofitBuilder
                .baseUrl(ApiConstans.sIFengApi)
                .build().create(NewsApiService.class));
    }

}
