package com.tjun.www.granatepro.ui.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.Constants;
import com.tjun.www.granatepro.bean.db.MyUser;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseActivity;
import com.tjun.www.granatepro.utils.DialogHelper;
import com.tjun.www.granatepro.utils.NetUtil;
import com.tjun.www.granatepro.utils.SpUtils;
import com.tjun.www.granatepro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by tanjun on 2018/3/19.
 */

public class LoginActivity extends BaseActivity {

    private Dialog dialog = null;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = DialogHelper.getMineLodingDiaLog(this, "登陆中...");
        boolean isChecked = SpUtils.getBoolean(this, Constants.IS_KEEP_PASS, false);
        mCbChoose.setChecked(isChecked);
        if (isChecked) {
            mEtUserName.setText(SpUtils.getString(this, Constants.USER_NAME, ""));
            mEtPassword.setText(SpUtils.getString(this, Constants.PASS_WORD, ""));
            SpUtils.getImageFromSp(this,mIvHead);
        }
    }

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

    @OnClick({R.id.tv_register, R.id.tv_forget, R.id.btn_login,R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.tv_forget://忘记密码应该走的逻辑,重置密码
                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.btn_login:
                if (!NetUtil.isMobileConnected(this) && !NetUtil.isWifiConnected(this)) {
                    ToastUtils.showShort(this, "当前无网络...");
                } else {
                    dialog.show();
                    String userName = mEtUserName.getText().toString().trim();
                    String userPassword = mEtPassword.getText().toString().trim();

                    final MyUser user = new MyUser();

                    user.setUsername(userName);
                    user.setPassword(userPassword);

                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null) {//登录成功
                                Intent intent = new Intent();
                                intent.putExtra("username", myUser.getUsername());
                                intent.putExtra("desc", myUser.getDesc());
                                if (myUser.getHeadImg() != null) {
                                    intent.putExtra("img",myUser.getHeadImg());
                                }
                                if (myUser.getBackgroundImg() != null){
                                    intent.putExtra("backImg",myUser.getBackgroundImg());
                                }
                                setResult(RESULT_OK, intent);
                                dialog.dismiss();
                                finish();
                                SpUtils.putBoolean(LoginActivity.this, Constants.IS_LOGIN, true);
                                //保存token
                                SpUtils.putString(LoginActivity.this, Constants.BMOB_TOKEN, myUser.getSessionToken());
                            } else {
                                dialog.dismiss();
                                ToastUtils.showShort(LoginActivity.this, "用户名或者密码错误");
                            }
                        }
                    });

                }

                break;
        }
    }

    /**
     * 在onpause中保存密码  可能是因为引入了注解框架
     */
    @Override
    protected void onPause() {
        super.onPause();
        SpUtils.putBoolean(this, Constants.IS_KEEP_PASS, mCbChoose.isChecked());

        //是否记住密码逻辑
        if (mCbChoose.isChecked()) {
            SpUtils.putString(this, Constants.USER_NAME, mEtUserName.getText().toString().trim());
            SpUtils.putString(this, Constants.PASS_WORD, mEtPassword.getText().toString().trim());
        } else {
            SpUtils.delete(this, Constants.USER_NAME);
            SpUtils.delete(this, Constants.PASS_WORD);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
