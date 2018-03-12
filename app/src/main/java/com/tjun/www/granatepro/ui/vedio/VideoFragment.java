package com.tjun.www.granatepro.ui.vedio;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.adapter.VideoPagerAdapter;
import com.tjun.www.granatepro.ui.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by tanjun on 2018/3/5.
 */

public class VideoFragment extends BaseFragment<VideoPresenter> implements VideoContract.View {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager videoViewPager;

    private VideoPagerAdapter mAdapter;


    public static VideoFragment newsInstance(){
        Bundle args = new Bundle();
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_video;
    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
    }

}
