package com.tjun.www.granatepro.ui.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.Constants;
import com.tjun.www.granatepro.bean.MyUser;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseActivity;
import com.tjun.www.granatepro.utils.SpUtils;
import com.tjun.www.granatepro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by tanjun on 2018/3/22.
 */

public class UpdateActivity extends BaseActivity {
    @BindView(R.id.et_new_password)
    EditText mEtNewsPassWord;
    @BindView(R.id.et_again_password)
    EditText mEtAgainPassWord;
    @BindView(R.id.et_old_password)
    EditText mEtOldPassWord;

    @BindView(R.id.btn_update)
    Button mBtnUpdate;

    @Override
    public int getContentLayout() {
        return R.layout.activity_update_password;
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

    @OnClick(R.id.btn_update)
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update:
                String oldPassWord = mEtOldPassWord.getText().toString().trim();
                String newPassWord = mEtNewsPassWord.getText().toString().trim();
                String againPassWord = mEtAgainPassWord.getText().toString().trim();

                if (oldPassWord != null && newPassWord != null && againPassWord != null
                        && newPassWord.equals(againPassWord)) {
                    //MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
                    BmobUser.updateCurrentUserPassword(oldPassWord, againPassWord, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                finish();
                                ToastUtils.showShort(UpdateActivity.this, "密码修改成功");
                            } else { //可以给个提示到忘记密码页面
                                ToastUtils.showShort(UpdateActivity.this,"原密码错误"+e.getMessage());
                            }
                        }
                    });
                }
                break;
        }
    }
}
