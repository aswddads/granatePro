package com.tjun.www.granatePro.ui.news.contract;

import com.tjun.www.granatePro.bean.NewsArticleBean;
import com.tjun.www.granatePro.ui.base.BaseContract;

/**
 * Created by tanjun on 2018/3/15.
 */
public interface ArticleReadContract {

    interface View extends BaseContract.BaseView {

        void loadData(NewsArticleBean articleBean);

    }

    interface Presenter extends BaseContract.BasePresenter<View>{

        void getData(String aid);

    }

}
