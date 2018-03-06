package com.tjun.www.granatepro.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.tjun.www.granatepro.R;

/**
 * Created by tanjun on 2018/3/5.
 * 状态视图
 */

public class SimpleMultiStateView extends MultiStateView {
    private static final String TAG = SimpleMultiStateView.class.getSimpleName();

    private static final int MIN_SHOW_TIME = 400;
    private static final int MIN_DELAY_TIME = 600;

    private int mTargetState = -1;
    private long mLoadingStartTime = -1;

    int resIdEmpty;
    int resIdLoading;
    int resIdFail;
    int resIdNoNet;

    private final Runnable mLoadingHide = new Runnable() {
        @Override
        public void run() {
            setViewState(mTargetState);
            mLoadingStartTime = -1;
            mTargetState = -1;
        }
    };

    public SimpleMultiStateView(@NonNull Context context) {
        this(context,null);
    }

    public SimpleMultiStateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public SimpleMultiStateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.msv_SimpleMultiStateView);
        resIdEmpty = typedArray.getResourceId(R.styleable.msv_SimpleMultiStateView_msv_emptyView,-1);
        resIdLoading = typedArray.getResourceId(R.styleable.msv_SimpleMultiStateView_msv_loadingView,-1);
        resIdFail = typedArray.getResourceId(R.styleable.msv_SimpleMultiStateView_msv_failView,-1);
        resIdNoNet = typedArray.getResourceId(R.styleable.msv_SimpleMultiStateView_msv_nonetView,-1);

        typedArray.recycle();

        if (typedArray != null) {
            if (resIdEmpty != -1) {
                addViewForStatus(MultiStateView.STATE_EMPTY, resIdEmpty);
            }
            if (resIdFail != -1) {
                addViewForStatus(MultiStateView.STATE_FAIL, resIdFail);
            }
            if (resIdLoading != -1) {
                addViewForStatus(MultiStateView.STATE_LOADING, resIdLoading);
            }
            if (resIdNoNet != -1) {
                addViewForStatus(MultiStateView.STATE_NO_NET, resIdNoNet);
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        removeCallbacks(mLoadingHide);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(mLoadingHide);
    }

    /**
     * 进度
     */
    public void showLoadingView(){
        setViewState(MultiStateView.STATE_LOADING);
    }

    public void showErrorView() {
        if (getViewState() != MultiStateView.STATE_CONTENT) {
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setViewState(MultiStateView.STATE_FAIL);
                }
            },200);
        }
    }

    public void showEmptyView() {
        if (getViewState() != MultiStateView.STATE_CONTENT) {
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setViewState(MultiStateView.STATE_EMPTY);
                }
            },100);
        }
    }

    public void shouContent(){
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                setViewState(MultiStateView.STATE_CONTENT);
            }
        },100);
    }

    @Override
    public void setViewState(int state) {
        if (getViewState()  == STATE_LOADING && state != STATE_LOADING) {
            long diff = System.currentTimeMillis() - mLoadingStartTime;
            if (diff < MIN_SHOW_TIME) {
                mTargetState = state;
                postDelayed(mLoadingHide,MIN_DELAY_TIME);
            } else {
                super.setViewState(state);
            }
            return;
        } else if (state == STATE_LOADING) {
            mLoadingStartTime = System.currentTimeMillis();
        }
        super.setViewState(state);
    }

    /**
     * 设置emptyView的自定义Layout
     * @param emptyResource
     * @return
     */
    public SimpleMultiStateView setEmptyResource(@LayoutRes int emptyResource) {
        this.resIdEmpty = emptyResource;
        addViewForStatus(MultiStateView.STATE_EMPTY,resIdEmpty);

        return this;
    }

    /**
     * 设置retryView的自定义Layout
     * @param retryResource
     * @return
     */
    public SimpleMultiStateView setRetryResource(@LayoutRes int retryResource) {
        this.resIdFail = retryResource;
        addViewForStatus(MultiStateView.STATE_FAIL,resIdFail);

        return this;
    }

    /**
     * 设置loadingView的自定义Layout
     * @param loadingResource
     * @return
     */
    public SimpleMultiStateView setLoadingResource(@LayoutRes int loadingResource) {
        this.resIdLoading = loadingResource;
        addViewForStatus(MultiStateView.STATE_LOADING,resIdLoading);

        return this;
    }

    /**
     * 设置noNetView的自定义Layout
     * @param noNetResource
     * @return
     */
    public SimpleMultiStateView setNoNetResource(@LayoutRes int noNetResource) {
        this.resIdNoNet = noNetResource;
        addViewForStatus(MultiStateView.STATE_NO_NET,resIdNoNet);

        return this;
    }

    public SimpleMultiStateView build(){
        showLoadingView();
        return this;
    }
}
