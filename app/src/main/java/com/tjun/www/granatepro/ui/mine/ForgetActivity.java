package com.tjun.www.granatepro.ui.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by tanjun on 2018/3/20.
 */

public class ForgetActivity extends BaseActivity{
    @BindView(R.id.et_new_password)
    EditText mEtNewsPassWord;
    @BindView(R.id.et_again_password)
    EditText mEtAgainPassWord;
    @BindView(R.id.et_old_password)
    EditText mEtOldPassWord;

    @Override
    public int getContentLayout() {
        return R.layout.activity_forget_password;
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
