package com.tjun.www.granatepro.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tjun.www.granatepro.bean.VideoChannelBean;

/**
 * Created by tanjun on 2018/3/12.
 */

public class VideoPagerAdapter extends FragmentStatePagerAdapter {

    private VideoChannelBean videoChannelBean;

    public VideoPagerAdapter(FragmentManager fm,VideoChannelBean videoChannelBean) {
        super(fm);
        this.videoChannelBean = videoChannelBean;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
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
