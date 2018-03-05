package com.tjun.www.granatepro.ui.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.utils.ContextUtils;

/**
 * Created by tanjun on 2018/3/5.
 */

public class BottomBarTab extends LinearLayout {
    private ImageView mIcon;
    private Context mContext;
    private TextView mTxt;
    private int mTabPosition = -1;
    private int icon;
    private static boolean isShow = false;

    public BottomBarTab(Context context, @DrawableRes int icon, String title) {
        this(context, null, icon,  title);
    }


    public BottomBarTab(Context context, AttributeSet attrs, int icon, String title) {
        this(context, attrs, 0, icon, title);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int defStyleAttr, int icon, String title) {
        super(context, attrs, defStyleAttr);
        init(context, icon, title);
    }

    private void init(Context context, int icon, String title) {
        mContext = context;
        this.icon = icon;
        setOrientation(VERTICAL);
        mIcon = new ImageView(context);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,getResources().getDisplayMetrics());
        LayoutParams params = new LayoutParams(size,size);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = ContextUtils.dip2px(context,2.5f);
        mIcon.setImageResource(icon);
        mIcon.setLayoutParams(params);

        LayoutParams txtParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txtParams.gravity = Gravity.CENTER_HORIZONTAL;
        txtParams.topMargin = ContextUtils.dip2px(context,4.2f);
        txtParams.bottomMargin = ContextUtils.dip2px(context,4.2f);
        mTxt = new TextView(context);
        mTxt.setText(title);
        mTxt.setTextSize(ContextUtils.dip2px(context,5.0f));
        mTxt.setLayoutParams(params);
        mTxt.setTextColor(ContextCompat.getColor(mContext, R.color.tab_unselect));
        addView(mIcon);
        addView(mTxt);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            mIcon.setColorFilter(ContextCompat.getColor(mContext,R.color.colorPrimary));
            mTxt.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        } else {
            mIcon.setColorFilter(ContextCompat.getColor(mContext,R.color.tab_unselect));
            mTxt.setTextColor(ContextCompat.getColor(mContext,R.color.tab_unselect));
        }
    }

    public void setTabPosition(int position){
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition(){
        return mTabPosition;
    }
}
