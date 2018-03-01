package com.tjun.www.granatepro.ui.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        super.onCreate(savedInstanceState);
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
}
