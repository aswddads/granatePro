package com.tjun.www.granatepro.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import javax.inject.Inject;

import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;

/**
 * Created by tanjun on 2018/3/5.
 */

public abstract class BaseFragment<T1 extends BaseContract.BasePresenter> extends SupportFragment
        implements IBase,BaseContract.BaseView {
    protected Context mContext;
    protected View mRootView;
    protected Dialog mLoadingDiag;
    Unbinder mUnbiner;

    @Nullable
    @Inject
    protected T1 mPresenter;


}
