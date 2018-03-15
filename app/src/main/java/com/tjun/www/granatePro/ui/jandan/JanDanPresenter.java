package com.tjun.www.granatePro.ui.jandan;

import android.util.Log;

import com.tjun.www.granatePro.bean.FreshNewsBean;
import com.tjun.www.granatePro.bean.JdDetailBean;
import com.tjun.www.granatePro.net.BaseObserver;
import com.tjun.www.granatePro.net.JanDanApi;
import com.tjun.www.granatePro.net.RxSchedulers;
import com.tjun.www.granatePro.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by tanjun on 2018/3/12.
 */
public class JanDanPresenter extends BasePresenter<JanDanContract.View> implements JanDanContract.Presenter {
    private static final String TAG = "JanDanPresenter";
    JanDanApi mJanDanApi;

    @Inject
    public JanDanPresenter(JanDanApi janDanApi) {
        this.mJanDanApi = janDanApi;
    }

    @Override
    public void getData(String type, int page) {
        if (type.equals(JanDanApi.TYPE_FRESH)) {
            getFreshNews(page);
        } else {
            getDetailData(type, page);
        }
    }

    @Override
    public void getFreshNews(final int page) {
        mJanDanApi.getFreshNews(page)
                .compose(RxSchedulers.<FreshNewsBean>applySchedulers())
                .compose(mView.<FreshNewsBean>bindToLife())
                .subscribe(new BaseObserver<FreshNewsBean>() {
                    @Override
                    public void onSuccess(FreshNewsBean freshNewsBean) {
                        if (page > 1) {
                            mView.loadMoreFreshNews(freshNewsBean);
                        } else {
                            mView.loadFreshNews(freshNewsBean);
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });

    }

    @Override
    public void getDetailData(final String type, final int page) {
        mJanDanApi.getJdDetails(type, page)
                .compose(RxSchedulers.<JdDetailBean>applySchedulers())
                .compose(mView.<JdDetailBean>bindToLife())
                .map(new Function<JdDetailBean, JdDetailBean>() {
                    @Override
                    public JdDetailBean apply(@NonNull JdDetailBean jdDetailBean) throws Exception {
                        for (JdDetailBean.CommentsBean bean : jdDetailBean.getComments()) {
                            if (bean.getPics() != null) {
                                if (bean.getPics().size() > 1) {
                                    bean.itemType = JdDetailBean.CommentsBean.TYPE_MULTIPLE;
                                } else {
                                    bean.itemType = JdDetailBean.CommentsBean.TYPE_SINGLE;
                                }
                            }
                        }
                        return jdDetailBean;
                    }
                })
                .subscribe(new BaseObserver<JdDetailBean>() {
                    @Override
                    public void onSuccess(JdDetailBean jdDetailBean) {
                        if (page > 1) {
                            mView.loadMoreDetailData(type, jdDetailBean);
                        } else {
                            mView.loadDetailData(type, jdDetailBean);
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        if (page > 1) {
                            mView.loadMoreDetailData(type, null);
                        } else {
                            mView.loadDetailData(type, null);
                        }
                        Log.i(TAG, "onFail: " + e.getMessage());
                    }
                });
    }
}
