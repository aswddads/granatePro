package com.tjun.www.granatepro.ui.movie.presenter;

import com.tjun.www.granatepro.MyApp;
import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.Channel;
import com.tjun.www.granatepro.ui.base.BasePresenter;
import com.tjun.www.granatepro.ui.movie.contract.MovieContract;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by tanjun on 2018/4/12.
 */

public class MoviePresenter extends BasePresenter<MovieContract.View> implements MovieContract.Presenter {

    @Inject
    public MoviePresenter() {

    }

    @Override
    public void getChannel() {
        List<Channel> myChannels = new ArrayList<>();
        List<String> channelName = Arrays.asList(MyApp.getContext().getResources().getStringArray(R.array.movie_channel));
        List<String> channelId = Arrays.asList(MyApp.getContext().getResources().getStringArray(R.array.movie_channel_id));

        List<Channel> channels = new ArrayList<>();

        for (int i = 0; i < channelName.size(); i++) {
            Channel channel = new Channel();
            channel.setChannelName(channelName.get(i));
            channel.setChannelId(channelId.get(i));
            channel.setChannelType(1);
            channel.setChannelSelect(true);

            myChannels.add(channel);
            channels.add(channel);
        }

        mView.loadData(myChannels);

    }
}
