package com.tjun.www.granatepro.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by tanjun on 2018/4/13.
 */

public class MyViewPager extends ViewPager {
    private boolean isLocked;

    public MyViewPager(Context context) {
        super(context);
        isLocked = false;
    }

    public MyViewPager(Context context, AttributeSet attrs, boolean isLocked) {
        super(context, attrs);
        this.isLocked = false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {//Touch处理
        if (!isLocked) {
            try {
                return super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !isLocked && super.onTouchEvent(ev);
    }
}
