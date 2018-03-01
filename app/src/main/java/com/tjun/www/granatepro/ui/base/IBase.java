package com.tjun.www.granatepro.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tjun.www.granatepro.component.ApplicationComponent;

/**
 * Created by tanjun on 2018/3/1.
 */

public interface IBase {
    View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    View getView();

    int getContentLayout();

    void initInjector(ApplicationComponent applicationComponent);

    void bindView(View view,Bundle savedInstanceState);

    void initData();

}
