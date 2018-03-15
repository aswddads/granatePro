package com.tjun.www.granatePro.ui.news.contract;

import com.tjun.www.granatePro.bean.Channel;
import com.tjun.www.granatePro.ui.base.BaseContract;

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
