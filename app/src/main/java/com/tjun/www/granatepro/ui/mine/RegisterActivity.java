package com.tjun.www.granatepro.ui.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.MyUser;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseActivity;
import com.tjun.www.granatepro.utils.CountDownTimerUtils;
import com.tjun.www.granatepro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.v3.BmobSMS.requestSMSCode;

/**
 * Created by tanjun on 2018/3/20.
 */

public class RegisterActivity extends BaseActivity {
    private boolean isMan = true;

    private String checkNum;

    @BindView(R.id.et_username)
    EditText mEtUserName;
    @BindView(R.id.et_password)
    EditText mEtPassword;

    @BindView(R.id.rg_sex)
    RadioGroup mRgSex;
    @BindView(R.id.rb_man)
    RadioButton mRbMan;
    @BindView(R.id.rb_women)
    RadioButton mRbWomen;

    @BindView(R.id.et_des)
    EditText mEtDes;

    @BindView(R.id.et_iphone)
    EditText mEtIphone;

    @BindView(R.id.et_check_number)
    EditText mEtCheckNumber;

    @BindView(R.id.btn_check)
    Button mBtnCheck;

    @BindView(R.id.btn_register)
    Button mBtnRegister;

    @Override
    public int getContentLayout() {
        return R.layout.activity_register;
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

    @OnClick({R.id.btn_check, R.id.btn_register})
    public void onViewClicked(View view) {
        String iphone = mEtIphone.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_check:
               requestSMSCode(iphone, "注册", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {
                            checkNum = integer.toString().trim();
                            ToastUtils.showShort(RegisterActivity.this,"验证码已通过短信发送到您手机上");
                            //传入时间单位为毫秒
                            CountDownTimerUtils utils = new CountDownTimerUtils(mBtnCheck,60 * 1000,1 * 1000);
                        }
                    }
                });
                break;
            case R.id.btn_register:
                String userName = mEtUserName.getText().toString().trim();
                String passWord = mEtPassword.getText().toString().trim();
                String desc = mEtDes.getText().toString().trim();
                String userCheckNum = mEtCheckNumber.getText().toString().trim();

                if (!TextUtils.isEmpty(userName)
                        && !TextUtils.isEmpty(passWord)
                        && !TextUtils.isEmpty(iphone)
                        && !TextUtils.isEmpty(userCheckNum)) {

                    mRgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            if (checkedId == R.id.rb_man) {
                                isMan = true;
                            } else if (checkedId == R.id.rb_women) {
                                isMan = false;
                            }
                        }
                    });

                    if (TextUtils.isEmpty(desc)) {
                        desc = "程序员 Asw.Tan";
                    }

                    //注册
                    MyUser myUser = new MyUser();
                    myUser.setMobilePhoneNumber(iphone);
                    myUser.setUserName(userName);
                    myUser.setPassWord(passWord);
                    myUser.setDesc(desc);
                    //myUser.setNumber(iphone);
                    myUser.setSex(isMan);

                    myUser.signOrLogin(userCheckNum, new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null) {
                                ToastUtils.showShort(RegisterActivity.this, "注册成功");
                                finish();
                            } else {
                                ToastUtils.showShort(RegisterActivity.this, "注册失败:" + e.toString());
                            }
                        }
                    });

                } else if (TextUtils.isEmpty(userName)) {
                    ToastUtils.showShort(this, "用户名不能为空");
                } else if (TextUtils.isEmpty(passWord)) {
                    ToastUtils.showShort(this, "用户密码不能为空");
                } else if (TextUtils.isEmpty(iphone)) {
                    ToastUtils.showShort(this, "请输入手机号码进行注册");
                } else if (TextUtils.isEmpty(userCheckNum)) {
                    ToastUtils.showShort(this, "请输入验证码进行用户手机号验证");
                }
                break;
        }
    }
}
