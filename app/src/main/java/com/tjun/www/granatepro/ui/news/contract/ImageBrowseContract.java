package com.tjun.www.granatepro.ui.news.contract;

import com.tjun.www.granatepro.bean.NewsArticleBean;
import com.tjun.www.granatepro.ui.base.BaseContract;

/**
 * Created by tanjun on 2018/3/12.
 */
public interface ImageBrowseContract {

    interface View extends BaseContract.BaseView {

        void loadData(NewsArticleBean newsArticleBean);

    }

    interface Presenter extends BaseContract.BasePresenter<View>{

        void getData(String aid,boolean isCmpp);

    }

}
