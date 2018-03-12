package com.tjun.www.granatepro.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by tanjun on 2018/3/12.
 * custom viewPager
 */

public class CustomViewPager extends ViewPager{

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);
    }

    /**
     * 去除页面切换时的滑动翻页效果
     * @param item
     * @param smoothScroll
     */
    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }
}
