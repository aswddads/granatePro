package com.tjun.www.granatepro.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

/**
 * Created by tanjun on 2018/3/5.
 */

public class BottomBar extends LinearLayout{
    private static final int TRANSLATE_DURATION_MILLIS = 200;
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private boolean isVisible = true;

    private LinearLayout mTabLayout;
    private LayoutParams mParams;
    private int mCurrentPosition;

    private OnTabSelectedListener mListener;

    public BottomBar(Context context) {
        this(context,null);
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(HORIZONTAL);

        mTabLayout = new LinearLayout(context);
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setOrientation(HORIZONTAL);
        addView(mTabLayout,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        mParams.weight = 1;
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public BottomBar addItem(final BottomBarTab bar){
        bar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) {
                    return;
                }
                int pos = bar.getTabPosition();
                if (mCurrentPosition == pos) {
                    mListener.onTabReSelected(pos);
                } else {
                    mListener.onTabSelected(pos,mCurrentPosition);
                    bar.setSelected(true);
                    mListener.onTabUnSelected(mCurrentPosition);
                    mTabLayout.getChildAt(mCurrentPosition).setSelected(false);
                    mCurrentPosition = pos;
                }
            }
        });
        bar.setTabPosition(mTabLayout.getChildCount());
        bar.setLayoutParams(mParams);
        mTabLayout.addView(bar);
        return this;
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener){
        mListener = onTabSelectedListener;
    }

    public void setCurrentItem(final int position){
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                mTabLayout.getChildAt(position).performClick();
            }
        });
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, mCurrentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        if (mCurrentPosition != ss.position) {
            mTabLayout.getChildAt(mCurrentPosition).setSelected(false);
            mTabLayout.getChildAt(ss.position).setSelected(true);
        }
        mCurrentPosition = ss.position;
    }

    public int getCurrentItemPosition() {
        return mCurrentPosition;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void hide(boolean anim) {
        toggle(false, anim, false);
    }

    public void show(boolean anim) {
        toggle(true, anim, false);
    }

    private void toggle(final boolean visible, final boolean animate, boolean force){
        if (isVisible != visible || force) {
            isVisible =visible;
            int height = getHeight();
            if (height == 0 && !force) {
                final ViewTreeObserver observer = getViewTreeObserver();
                if (observer.isAlive()) {
                    // view树完成测量并且分配空间而绘制过程还没有开始的时候播放动画。
                    observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            ViewTreeObserver observer1 = getViewTreeObserver();
                            if (observer1.isAlive()) {
                                observer1.removeOnPreDrawListener(this);
                            }
                            toggle(visible,animate,true);
                            return true;
                        }
                    });
                    return;
                }
            }
            int translationY = visible ? 0 : height;
            if (animate) {
                animate().setInterpolator(mInterpolator)
                        .setDuration(TRANSLATE_DURATION_MILLIS)
                        .translationY(translationY);
            } else {
                ViewCompat.setTranslationY(this, translationY);
            }
        }
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int position,int prePosition);

        void onTabUnSelected(int position);

        void onTabReSelected(int position);
    }

    static class SavedState extends BaseSavedState {
        private int position;

        public SavedState(Parcel source) {
            super(source);
            position = source.readInt();
        }

        public SavedState(Parcelable superState, int position) {
            super(superState);
            this.position = position;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(position);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
