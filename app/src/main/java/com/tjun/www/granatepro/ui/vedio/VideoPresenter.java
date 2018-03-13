package com.tjun.www.granatepro.ui.vedio;

import com.tjun.www.granatepro.net.NewsApi;
import com.tjun.www.granatepro.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by tanjun on 2018/3/12.
 */

public class VideoPresenter extends BasePresenter<VideoContract.View> implements VideoContract.Presenter{
    private NewsApi mNewsApi;

    @Inject
    VideoPresenter(NewsApi newsApi) {
        this.mNewsApi = newsApi;
    }

}
