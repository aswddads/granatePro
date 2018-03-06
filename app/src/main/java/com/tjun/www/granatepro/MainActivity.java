package com.tjun.www.granatepro;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseActivity;
import com.tjun.www.granatepro.ui.base.SupportFragment;
import com.tjun.www.granatepro.ui.widget.BottomBar;
import com.tjun.www.granatepro.utils.StatusBarUtils;
import com.tjun.www.granatepro.utils.ToastUtils;

import butterknife.BindView;

/**
 * Created by tanjun on 2018/3/5.
 */

public class MainActivity extends BaseActivity{

    private long firstTime;

    @BindView(R.id.fl_content)
    private FrameLayout mFrameContent;
    @BindView(R.id.bottomBar)
    private BottomBar mBottomBar;

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
//            mFragments[0] =
        }
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
