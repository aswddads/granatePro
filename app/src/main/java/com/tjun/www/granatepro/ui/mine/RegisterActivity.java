package com.tjun.www.granatepro.ui.mine;

import android.app.ActionBar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.MyUser;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseActivity;
import com.tjun.www.granatepro.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

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

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
                            /**
                             * 注：
                             * 同一个手机号码  短时间内只能发10次验证码
                             */
                            ToastUtils.showShort(RegisterActivity.this,"验证码已通过短信发送到您手机上");
                            //传入时间单位为毫秒
                            //CountDownTimerUtils utils = new CountDownTimerUtils(mBtnCheck,60 * 1000,1 * 1000);
                            mCompositeDisposable.add(countDown(58).doOnSubscribe(new Consumer<Disposable>() {
                                @Override
                                public void accept(@NonNull Disposable disposable) throws Exception {
                                    mBtnCheck.setClickable(false);
                                    mBtnCheck.setText("59秒后可重发");
                                    mBtnCheck.setBackgroundResource(R.drawable.btn_number_send);
                                }
                            }).subscribeWith(new DisposableObserver<Integer>() {
                                @Override
                                public void onNext(Integer integer) {
                                    //mBtnCheck.setText("跳过 " + (integer + 1));
                                    mBtnCheck.setText((integer + 1) + "秒后可重发");
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                    mBtnCheck.setClickable(true);
                                    mBtnCheck.setText("重新获取验证码");
                                    mBtnCheck.setBackgroundResource(R.drawable.btn_check_bg);
                                }
                            }));
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
                    myUser.setUsername(userName);
                    myUser.setPassword(passWord);
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
                                ToastUtils.showShort(RegisterActivity.this, "注册失败，验证码有误或手机号码有误:" + e.toString());
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

    public Observable<Integer> countDown(int time) {
        if (time < 0) time = 0;
        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(@NonNull Long aLong) throws Exception {
                        return countTime - aLong.intValue();
                    }
                })
                .take(countTime + 1);
    }

    /**
     * 解决注册成功后crash
     */
    @Override
    protected void onDestroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        super.onDestroy();
    }

}
