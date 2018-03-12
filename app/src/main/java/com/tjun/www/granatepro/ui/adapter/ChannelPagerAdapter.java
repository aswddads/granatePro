package com.tjun.www.granatepro.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tjun.www.granatepro.bean.Channel;

import java.util.List;

/**
 * Created by tanjun on 2018/3/12.
 */

public class ChannelPagerAdapter extends FragmentStatePagerAdapter {

    private List<Channel> mChannels;

    public ChannelPagerAdapter(FragmentManager fm,List<Channel> channels) {
        super(fm);
        this.mChannels = channels;
    }

    public void uploadChannel(List<Channel> channelList) {
        this.mChannels = channelList;
        notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return mChannels != null ? mChannels.size() : 0;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
