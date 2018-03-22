package com.tjun.www.granatepro.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.MyUser;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseActivity;
import com.tjun.www.granatepro.utils.ToastUtils;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by tanjun on 2018/3/19.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.iv_head)
    ImageView mIvHead;

    @BindView(R.id.et_username)
    EditText mEtUserName;
    @BindView(R.id.et_password)
    EditText mEtPassword;

    @BindView(R.id.cb_choose)
    CheckBox mCbChoose;

    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.tv_forget)
    TextView mTvForGet;


    @Override
    public int getContentLayout() {
        return R.layout.activity_login;
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

    @OnClick({R.id.tv_register,R.id.tv_forget,R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.tv_forget://忘记密码应该走的逻辑,重置密码
                startActivity(new Intent(LoginActivity.this,ForgetActivity.class));
                break;
            case R.id.btn_login:
                String userName = mEtUserName.getText().toString().trim();
                String userPassword = mEtPassword.getText().toString().trim();

                MyUser user = new MyUser();

                user.setUserName(userName);
                user.setPassWord(userPassword);

                user.login(new SaveListener<MyUser>() {
                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if (e == null) {//登录成功

                            finish();
                        } else {
                            ToastUtils.showShort(LoginActivity.this,"用户名或者密码错误");
                        }
                    }
                });
                break;
        }
    }
}
