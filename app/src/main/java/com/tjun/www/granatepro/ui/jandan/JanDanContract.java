package com.tjun.www.granatepro.ui.jandan;

import com.tjun.www.granatepro.bean.FreshNewsBean;
import com.tjun.www.granatepro.bean.JdDetailBean;
import com.tjun.www.granatepro.ui.base.BaseContract;

/**
 * Created by tanjun on 2018/3/14.
 */
public interface JanDanContract {

    interface View extends BaseContract.BaseView {

        void loadFreshNews(FreshNewsBean freshNewsBean);

        void loadMoreFreshNews(FreshNewsBean freshNewsBean);

        void loadDetailData(String type, JdDetailBean jdDetailBean);

        void loadMoreDetailData(String type, JdDetailBean jdDetailBean);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getData(String type, int page);

        void getFreshNews(int page);

        void getDetailData(String type, int page);

    }
}
