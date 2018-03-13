package com.tjun.www.granatepro.ui.main;

import android.os.Bundle;
import android.view.View;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseContract;
import com.tjun.www.granatepro.ui.base.BaseFragment;
import com.tjun.www.granatepro.ui.base.BasePresenter;

/**
 * Created by tanjun on 2018/3/5.
 */

public class MainFragment extends BaseFragment<BasePresenter> implements BaseContract.BaseView{
    @Override
    public int getContentLayout() {
        return R.layout.fragment_maine;
    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }
}
