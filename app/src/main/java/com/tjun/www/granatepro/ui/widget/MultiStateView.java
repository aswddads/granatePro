package com.tjun.www.granatepro.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tjun.www.granatepro.R;

/**
 * Created by tanjun on 2018/3/5.
 */

public class MultiStateView extends FrameLayout {
    public static final int STATE_CONTENT = 1000;
    public static final int STATE_LOADING = 1001;
    public static final int STATE_EMPTY = 1002;
    public static final int STATE_FAIL = 1003;
    public static final int STATE_NO_NET = 1004;

    private SparseArray<View> mStateViewArray = new SparseArray<>();
    private SparseIntArray mLayoutIdArray = new SparseIntArray();
    private View mContentView;
    private int mCurrentState = STATE_CONTENT;

    private OnInflaterListener mOnInflaterListener;
    private onReloadListener mOnReloadListener;

    public MultiStateView(@NonNull Context context) {
        this(context,null);
    }

    public MultiStateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MultiStateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child) {
        validContentView(child);
        super.addView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        validContentView(child);
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int index) {
        validContentView(child);
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int width, int height) {
        validContentView(child);
        super.addView(child, width, height);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        validContentView(child);
        super.addView(child, index, params);
    }

    /**
     * 改变视图状态
     * @param state
     */
    public void setViewState(int state) {
        if (getCurrentView() == null) {
            return;
        }
        if (state != mCurrentState) {
            View view = getView(state);
            getCurrentView().setVisibility(GONE);
            mCurrentState = state;
            if (view != null){
                view.setVisibility(VISIBLE);
            } else {
                int resLayoutId = mLayoutIdArray.get(state);
                if (resLayoutId == 0) return;
                view = LayoutInflater.from(getContext()).inflate(resLayoutId,this,false);
                mStateViewArray.put(state,view);
                addView(view);
                if (state == STATE_FAIL) {
                    View bt = view.findViewById(R.id.rl_retry);
                    if (bt != null) {
                        bt.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mOnReloadListener != null) {
                                    mOnReloadListener.onReload();
                                    setViewState(STATE_LOADING);
                                }
                            }
                        });
                    }
                }
                view.setVisibility(VISIBLE);
                if (mOnInflaterListener != null) {
                    mOnInflaterListener.onInflate(state,view);
                }
            }
        }
    }

    public void addViewForStatus(int status,int resLayoutID){
        mLayoutIdArray.put(status,resLayoutID);
    }

    public void setmOnReloadListener(onReloadListener onReLoadListener){
        mOnReloadListener = onReLoadListener;
    }

    public void setmOnInflaterListener(OnInflaterListener onInflaterListener){
        mOnInflaterListener = onInflaterListener;
    }


    /**
     * 获取当前view
     * @return
     */
    public View getCurrentView(){
        if (mCurrentState == -1) return null;
        View view = getView(mCurrentState);
        if (view == null && mCurrentState == STATE_CONTENT) {
            throw new NullPointerException("no content");
        } else if (view == null) {
            throw new NullPointerException("current state view is null, state = " + mCurrentState);
        }
        return view;
    }

    /**
     * 获取当前状态
     * @return
     */
    public int getViewState(){
        return mCurrentState;
    }

    /**
     * 获取制定状态的view
     * @param mCurrentState
     * @return
     */
    private View getView(int mCurrentState) {
        return mStateViewArray.get(mCurrentState);
    }

    private boolean isValidView(View view) {
        if (mContentView == null) {
            for (int i = 0; i < mStateViewArray.size(); i++) {
                if (mStateViewArray.indexOfValue(view) != -1) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 检查当前view是否为content
     */
    private void validContentView(View view){
        if (isValidView(view)) {
            mContentView = view;
            mStateViewArray.put(STATE_CONTENT,view);
        } else if(mCurrentState != STATE_CONTENT) {
            mContentView.setVisibility(GONE);
        }
    }

    /**
     * inflate监听
     */
    public interface OnInflaterListener {
        void onInflate(int state,View view);
    }

    /**
     * 重新加载接口
     */
    public interface onReloadListener {
        void onReload();
    }
}
