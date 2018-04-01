package com.tjun.www.granatepro.ui.mine;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by tanjun on 2018/3/27.
 */

public class MyCollectingActivity extends BaseActivity{

    @BindView(R.id.iv_back)
    TextView mIvBack;
    @BindView(R.id.recycler_my_collect)
    RecyclerView mRecycler;

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_collect;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onRetry() {

    }
}
