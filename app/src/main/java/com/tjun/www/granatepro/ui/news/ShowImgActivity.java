package com.tjun.www.granatepro.ui.news;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseActivity;
import com.tjun.www.granatepro.utils.GildUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by tanjun on 2018/4/12.
 */

public class ShowImgActivity extends BaseActivity{

    @BindView(R.id.vp_img)
    ViewPager mViewPager;
    private View rootView;
    @BindView(R.id.photoview)
    PhotoView image;
    @BindView(R.id.indicator)
    TextView indicator;

    private Bundle bundle;
    ArrayList<String> listimg;
    private int index;
    private int count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_show_img;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
    }

    @Override
    public void initData() {
        bundle = getIntent().getBundleExtra("bundle");
        listimg = bundle.getStringArrayList("img_list");
        index = bundle.getInt("index");
        count = listimg.size();

        mViewPager.setAdapter(new ImgPagerAdapter());

        mViewPager.setCurrentItem(index);
    }

    @Override
    public void onRetry() {

    }

    class ImgPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            rootView = View.inflate(ShowImgActivity.this,R.layout.item_img,null);
            GildUtils.LoadImage(ShowImgActivity.this,listimg.get(position),image);
            CharSequence text = getString(R.string.viewpager_indicator,position+1,count);
            indicator.setText(text);
            container.addView(rootView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
            return rootView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
