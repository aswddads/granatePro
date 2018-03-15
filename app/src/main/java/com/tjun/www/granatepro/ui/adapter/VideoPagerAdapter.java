package com.tjun.www.granatePro.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tjun.www.granatePro.bean.VideoChannelBean;
import com.tjun.www.granatePro.ui.base.BaseFragment;
import com.tjun.www.granatePro.ui.video.DetailFragment;

/**
 * Created by tanjun on 2018/3/13.
 */
public class VideoPagerAdapter extends FragmentStatePagerAdapter {

    private VideoChannelBean videoChannelBean;

    public VideoPagerAdapter(FragmentManager fm, VideoChannelBean videoChannelBean) {
        super(fm);
        this.videoChannelBean = videoChannelBean;
    }

    @Override
    public BaseFragment getItem(int position) {
        return DetailFragment.newInstance("clientvideo_" + videoChannelBean.getTypes().get(position).getId());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return videoChannelBean.getTypes().get(position).getName();
    }

    @Override
    public int getCount() {
        return videoChannelBean != null ? videoChannelBean.getTypes().size() : 0;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
