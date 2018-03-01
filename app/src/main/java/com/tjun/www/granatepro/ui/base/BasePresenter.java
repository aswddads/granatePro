package com.tjun.www.granatepro.ui.base;

/**
 * Created by tanjun on 2018/3/1.
 */

public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T>{

    protected T mView;
    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }
}
