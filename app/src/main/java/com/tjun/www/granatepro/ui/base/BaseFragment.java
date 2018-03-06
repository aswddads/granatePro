package com.tjun.www.granatepro.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tjun.www.granatepro.MyApp;
import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.ui.widget.MultiStateView;
import com.tjun.www.granatepro.ui.widget.SimpleMultiStateView;
import com.tjun.www.granatepro.utils.DialogUtils;
import com.tjun.www.granatepro.utils.ToastUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;

/**
 * Created by tanjun on 2018/3/5.
 */

public abstract class BaseFragment<T1 extends BaseContract.BasePresenter> extends SupportFragment
        implements IBase,BaseContract.BaseView {
    protected Context mContext;
    protected View mRootView;
    protected Dialog mLoadingDialog;
    Unbinder mUnbiner;

    @Nullable
    @Inject
    protected T1 mPresenter;

    SimpleMultiStateView mSimpleMultiStateView;

    @android.support.annotation.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @android.support.annotation.Nullable ViewGroup container, @android.support.annotation.Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup group = (ViewGroup) mRootView.getParent();
            if (group != null) {
                group.removeView(group);
            }
        } else {
            mRootView = createView(inflater,container,savedInstanceState);
        }

        mContext = mRootView.getContext();
        mLoadingDialog = DialogUtils.getLoadingDialog(getActivity());
        return mRootView;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayout(),container,false);
        mUnbiner = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInjector(MyApp.getInstance().getApplicationComponent());
        attachView();
        bindView(view,savedInstanceState);
        initStateView();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @android.support.annotation.Nullable
    @Override
    public View getView() {
        return mRootView;
    }

    @Override
    public void onLazyInitView(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initData();
    }

    private void initStateView(){
        if (mSimpleMultiStateView == null) return;
        mSimpleMultiStateView.setEmptyResource(R.layout.view_empty)
                .setRetryResource(R.layout.view_retry)
                .setLoadingResource(R.layout.view_loading)
                .setNoNetResource(R.layout.view_nonet)
                .build()
                .setmOnReloadListener(new MultiStateView.onReloadListener() {
                    @Override
                    public void onReload() {
                        onRetry();
                    }
                });
    }

    @Override
    public void showLoading() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showLoadingView();
        }
    }

    @Override
    public void showSuccess() {
        hideLoadingDialog();
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showContent();
        }
    }

    @Override
    public void showFailed() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showErrorView();
        }
    }

    @Override
    public void showNoNet() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showNoNetView();
        }
    }

    protected void ToastMsg(String string) {
        ToastUtils.showShort(MyApp.getContext(), string);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    @Override
    public void onRetry() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbiner.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected void showLoadingDialog() {
        if (mLoadingDialog != null)
            mLoadingDialog.show();
    }

    protected void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }
}
