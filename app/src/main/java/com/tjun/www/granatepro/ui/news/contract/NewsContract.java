package com.tjun.www.granatepro.ui.news.contract;

import com.tjun.www.granatepro.bean.Channel;
import com.tjun.www.granatepro.ui.base.BaseContract;

import java.util.List;

/**
 * Created by tanjun on 2018/3/10.
 */
public interface NewsContract{

    interface View extends BaseContract.BaseView {

        void loadData(List<Channel> channels, List<Channel> otherChannels);

    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        /**
         * 初始化频道
         */
        void getChannel();

    }

}
