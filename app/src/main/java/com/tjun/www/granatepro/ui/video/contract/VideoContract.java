package com.tjun.www.granatepro.ui.video.contract;

import com.tjun.www.granatepro.bean.VideoChannelBean;
import com.tjun.www.granatepro.bean.VideoDetailBean;
import com.tjun.www.granatepro.ui.base.BaseContract;

import java.util.List;

/**
 * Created by tanjun on 2018/3/12.
 */
public interface VideoContract {

    interface View extends BaseContract.BaseView {

        void loadVideoChannel(List<VideoChannelBean> channelBean);

        void loadVideoDetails(List<VideoDetailBean> detailBean);

        void loadMoreVideoDetails(List<VideoDetailBean> detailBean);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        /**
         * 获取视频频道列表
         */
        void getVideoChannel();

        /**
         * 获取视频列表
         *
         * @param page     页码
         * @param listType 默认list
         * @param typeId   频道id
         */
        void getVideoDetails(int page, String listType, String typeId);
    }
}
