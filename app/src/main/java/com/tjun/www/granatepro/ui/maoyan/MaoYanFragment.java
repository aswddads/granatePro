package com.tjun.www.granatepro.ui.maoyan;

import android.os.Bundle;
import android.view.View;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.Channel;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseFragment;
import com.tjun.www.granatepro.ui.news.NewsContract;
import com.tjun.www.granatepro.ui.news.NewsPresenter;

import java.util.List;

/**
 * Created by tanjun on 2018/3/13.
 */

public class MaoYanFragment extends BaseFragment<NewsPresenter> implements NewsContract.View {
    @Override
    public void loadData(List<Channel> channels, List<Channel> otherChannel) {

    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_maoyan;
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
