package com.tjun.www.granatepro.ui.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tjun.www.granatepro.MyApp;
import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.utils.DialogUtils;
import com.tjun.www.granatepro.utils.StatusBarUtils;
import com.tjun.www.granatepro.utils.ToastUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import io.reactivex.annotations.Nullable;


/**
 * Created by tanjun on 2018/3/1.
 */

public abstract class BaseActivity<T1 extends BaseContract.BasePresenter> extends SupportActivity implements IBase,BaseContract.BaseView,BGASwipeBackHelper.Delegate{
    protected View mRootView;
    protected Dialog mLoadingDialog = null;

    Unbinder unbinder;

    @Nullable
    @Inject
    protected T1 mPresenter;
    protected BGASwipeBackHelper mSwipeBackHelper;


    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        mRootView = createView(null,null,savedInstanceState);
        setContentView(mRootView);
        attachView();
        bindView(mRootView,savedInstanceState);
        initData();
        mLoadingDialog = DialogUtils.getLoadingDialog(this);
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this,this);
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(false);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(getContentLayout(),container);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public View getView() {
        return mRootView;
    }

    private void attachView(){
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     * @param statusBarAlpha 透明度
     */
    public void setStatusBarColor(@ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        StatusBarUtils.setColorForSwipeBack(this, color, statusBarAlpha);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected void T(String string) {
        ToastUtils.showShort(MyApp.getContext(), string);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }


    @Override
    public void onSwipeBackLayoutSlide(float v) {

    }

    @Override
    public void onSwipeBackLayoutCancel() {

    }

    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }
}
