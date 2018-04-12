package com.tjun.www.granatepro.ui.movie.contract;

import com.tjun.www.granatepro.bean.Channel;
import com.tjun.www.granatepro.ui.base.BaseContract;
import com.tjun.www.granatepro.ui.news.contract.NewsContract;

import java.util.List;

/**
 * Created by tanjun on 2018/4/11.
 */

public interface MovieContract {

    interface View extends BaseContract.BaseView {

        void loadData(List<Channel> channels);

    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        /**
         * 初始化频道
         */
        void getChannel();

    }
}
