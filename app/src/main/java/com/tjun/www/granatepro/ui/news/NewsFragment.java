package com.tjun.www.granatepro.ui.news;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;

import com.flyco.tablayout.SlidingTabLayout;
import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.Channel;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.adapter.ChannelPagerAdapter;
import com.tjun.www.granatepro.ui.base.BaseFragment;
import com.tjun.www.granatepro.ui.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by tanjun on 2018/3/5.
 */

public class NewsFragment extends BaseFragment<NewsPresenter> implements NewsContract.View{

    @BindView(R.id.view_pager)
    CustomViewPager mViewPager;
    @BindView(R.id.iv_add)
    ImageButton mIvAdd;
    @BindView(R.id.news_tab_layout)
    SlidingTabLayout mTabLayout;

    private ChannelPagerAdapter mAdapter;

    private List<Channel> mSelectChannels;
    private List<Channel> mUnSelectChannels;
    private int selectIndex;
    private String selectChannel;

    public static NewsFragment newsInstance(){
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void loadData(List<Channel> channels, List<Channel> otherChannel) {
        if (channels != null) {
            mSelectChannels.clear();
            mSelectChannels.addAll(channels);
            mUnSelectChannels.clear();
            mUnSelectChannels.addAll(otherChannel);
            mAdapter = new ChannelPagerAdapter(getChildFragmentManager(),channels);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setOffscreenPageLimit(2);
            mViewPager.setCurrentItem(0,false);
            mTabLayout.setViewPager(mViewPager);
        } else {
            ToastMsg("数据异常");
        }
    }

    /**
     * 设置当前选中项
     * @param strings
     * @param channelName
     */
    public void setViewPagerPosition(List<String> strings,String channelName){
        if (TextUtils.isEmpty(channelName) || strings == null){
            return;
        }
        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i).equals(channelName)) {
                selectChannel = strings.get(i);
                selectIndex = i;
                break;
            }
        }
        mViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewPager.setCurrentItem(selectIndex,false);
            }
        },100);
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_news;
    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectIndex = position;
                selectChannel = mSelectChannels.get(position).getChannelName();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        mSelectChannels = new ArrayList<>();
        mUnSelectChannels = new ArrayList<>();
        mPresenter.getChannel();
    }

    @OnClick(R.id.iv_add)
    public void onViewClicked(){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
