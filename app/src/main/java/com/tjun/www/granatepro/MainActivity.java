package com.tjun.www.granatepro;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseActivity;
import com.tjun.www.granatepro.ui.base.SupportFragment;
import com.tjun.www.granatepro.ui.news.NewsFragment;
import com.tjun.www.granatepro.ui.vedio.VideoFragment;
import com.tjun.www.granatepro.ui.widget.BottomBar;
import com.tjun.www.granatepro.ui.widget.BottomBarTab;
import com.tjun.www.granatepro.utils.StatusBarUtils;
import com.tjun.www.granatepro.utils.ToastUtils;

import butterknife.BindView;

/**
 * Created by tanjun on 2018/3/5.
 */

public class MainActivity extends BaseActivity{

    private long firstTime;

    @BindView(R.id.fl_content)
    FrameLayout mFrameContent;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    private SupportFragment[] mFragments = new SupportFragment[4];

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showFailed() {

    }

    @Override
    public void showNoNet() {

    }

    @Override
    public void onRetry() {

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        StatusBarUtils.setTranslucentForImageViewInFragment(this,0,null);
        if (savedInstanceState == null) {
            mFragments[0] = NewsFragment.newsInstance();
            mFragments[1] = VideoFragment.newsInstance();

            getSupportDelegate().loadMultipleRootFragment(R.id.fl_content,0,mFragments[0],mFragments[1]);
        } else {
            mFragments[0] = findFragment(NewsFragment.class);
            mFragments[1] = findFragment(VideoFragment.class);
        }

        mBottomBar.addItem(new BottomBarTab(this,R.drawable.ic_news,"新闻"));
        mBottomBar.addItem(new BottomBarTab(this,R.drawable.ic_video,"视频"));
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                getSupportDelegate().showHideFragment(mFragments[position],mFragments[prePosition]);
            }

            @Override
            public void onTabUnSelected(int position) {

            }

            @Override
            public void onTabReSelected(int position) {

            }
        });
    }

    @Override
    public void initData() {

    }

    /**
     * 两秒内连续点击两次back键退出
     */
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstTime < 200) {
            super.onBackPressed();
        } else {
            ToastUtils.showLong(getApplicationContext(),"再次点击退出");
            firstTime = System.currentTimeMillis();
        }
    }
}
