package com.tjun.www.granatepro.ui.movie;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.Channel;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.component.DaggerHttpComponent;
import com.tjun.www.granatepro.ui.adapter.ChannelPagerAdapter;
import com.tjun.www.granatepro.ui.base.BaseFragment;
import com.tjun.www.granatepro.ui.movie.contract.MovieContract;
import com.tjun.www.granatepro.ui.movie.presenter.MoviePresenter;
import com.tjun.www.granatepro.utils.ToastUtils;
import com.tjun.www.granatepro.widget.CustomViewPager;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by tanjun on 2018/4/12.
 */

public class MovieFragment extends BaseFragment<MoviePresenter> implements MovieContract.View {

    @BindView(R.id.viewpager)
    CustomViewPager mViewpager;
    @BindView(R.id.iv_edit)
    ImageView mIvEdit;
    @BindView(R.id.SlidingTabLayout)
    com.flyco.tablayout.SlidingTabLayout SlidingTabLayout;

    private ChannelPagerAdapter mChannelPagerAdapter;

    private List<Channel> mSelectedDatas;

    private int selectedIndex;
    private String selectedChannel;


    public static MovieFragment newInstance() {
        Bundle args = new Bundle();
        MovieFragment movieFragment = new MovieFragment();
        movieFragment.setArguments(args);
        return movieFragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_news_new;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder().applicationComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        mIvEdit.setVisibility(View.GONE);
        EventBus.getDefault().register(this);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedIndex = position;
                selectedChannel = mSelectedDatas.get(position).getChannelName();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    /**
     * 设置 当前选中页
     *
     * @param integers
     * @param channelName
     */
    private void setViewpagerPosition(List<String> integers, String channelName) {
        if (TextUtils.isEmpty(channelName) || integers == null) return;
        for (int j = 0; j < integers.size(); j++) {
            if (integers.get(j).equals(channelName)) {
                selectedChannel = integers.get(j);
                selectedIndex = j;
                break;
            }
        }
        mViewpager.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewpager.setCurrentItem(selectedIndex, false);
            }
        }, 100);
    }


    @Override
    public void initData() {
        mSelectedDatas = new ArrayList<>();
        mPresenter.getChannel();

    }

    @Override
    public void loadData(List<Channel> channels) {

        if (channels != null) {
            mSelectedDatas.clear();
            mSelectedDatas.addAll(channels);
            mChannelPagerAdapter = new ChannelPagerAdapter(getChildFragmentManager(), channels);
            mViewpager.setAdapter(mChannelPagerAdapter);
            mViewpager.setOffscreenPageLimit(2);//预加载
            mViewpager.setCurrentItem(0, false);
            SlidingTabLayout.setViewPager(mViewpager);
        } else {
            ToastUtils.showShort(getActivity(),"数据异常");
        }
    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
