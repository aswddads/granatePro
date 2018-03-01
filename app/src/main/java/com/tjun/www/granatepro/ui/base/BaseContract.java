package com.tjun.www.granatepro.ui.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by tanjun on 2018/3/1.
 */

public interface BaseContract {
    interface BasePresenter<T extends BaseContract.BaseView> {
        void attachView(T view);

        void detachView();
    }

    interface BaseView {
        //        显示进度中
        void showLoading();

        //请求成功
        void showSuccess();

        //失败
        void showFailed();

        //无网络
        void showNoNet();

        //重试
        void onRetry();

        <T> LifecycleTransformer<T> bindToLife();
    }
}
