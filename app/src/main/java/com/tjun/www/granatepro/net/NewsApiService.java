package com.tjun.www.granatepro.net;

import com.tjun.www.granatepro.bean.NewsArticleBean;
import com.tjun.www.granatepro.bean.NewsCmppVideoBean;
import com.tjun.www.granatepro.bean.NewsDetail;
import com.tjun.www.granatepro.bean.NewsImagesBean;
import com.tjun.www.granatepro.bean.VideoChannelBean;
import com.tjun.www.granatepro.bean.VideoDetailBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;


/**
 * Created by tanjun on 2018/3/12.
 */
public interface NewsApiService {


//    @GET("ClientNews")
//    Observable<List<NewsDetail>> getNewsDetail(@Query("id") String id,
//                                               @Query("action") String action,
//                                               @Query("pullNum") String pullNum,
//                                               @Query("uid") String uid,
//                                               @Query("devid") String devid,
//                                               @Query("proid") String proid,
//                                               @Query("vt") String vt,
//                                               @Query("publishid") String publishid,
//                                               @Query("screen") String screen,
//                                               @Query("os") String os,
//                                               @Query("df") String df,
//                                               @Query("nw") String nw);

    @GET("ClientNews")
    Observable<List<NewsDetail>> getNewsDetail(@Query("id") String id,
                                               @Query("action") String action,
                                               @Query("pullNum") int pullNum
    );

    @GET("api_vampire_article_detail")
    Observable<NewsArticleBean> getNewsArticleWithSub(@Query("aid") String aid);

    @GET
    Observable<NewsArticleBean> getNewsArticleWithCmpp(@Url String url,
                                                       @Query("aid") String aid);

    @GET
    Observable<NewsImagesBean> getNewsImagesWithCmpp(@Url String url,
                                                     @Query("aid") String aid);

    @GET("NewRelativeVideoList")
    Observable<NewsCmppVideoBean> getNewsVideoWithCmpp(@Url String url,
                                                       @Query("guid") String guid);

    @GET("ifengvideoList")
    Observable<List<VideoChannelBean>> getVideoChannel(@Query("page") int page);

    @GET("ifengvideoList")
    Observable<List<VideoDetailBean>> getVideoDetail(@Query("page") int page,
                                                     @Query("listtype") String listtype,
                                                     @Query("typeid") String typeid);


}
