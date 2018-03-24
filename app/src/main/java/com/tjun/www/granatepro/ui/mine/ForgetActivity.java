package com.tjun.www.granatepro.ui.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.print.PageRange;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.Constants;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseActivity;
import com.tjun.www.granatepro.utils.DialogHelper;
import com.tjun.www.granatepro.utils.SpUtils;
import com.tjun.www.granatepro.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;



/**
 * Created by tanjun on 2018/3/20.
 */

public class ForgetActivity extends BaseActivity {
    private Dialog dialog = null;
    private String currentTime = null;
    private String endTime = null;

    @BindView(R.id.et_iphone)
    EditText mEtIphone;
    @BindView(R.id.et_password1)
    EditText mEtPassWord1;
    @BindView(R.id.et_password2)
    EditText mEtPassWord2;

    @BindView(R.id.et_check_number)
    EditText mEtCheckNumber;
    @BindView(R.id.btn_check)
    Button mBtnCheck;

    @BindView(R.id.btn_register)
    Button mBtnRegister;

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private String update_code = null;



    @Override
    public int getContentLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = DialogHelper.getMineLodingDiaLog(this,"重置中...");
        Intent intent = getIntent();
        update_code = intent.getStringExtra("this");
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
                if (!TextUtils.isEmpty(iphone)) {
                    BmobSMS.requestSMSCode(iphone, "新鲜事", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (e == null) {
                                ToastUtils.showShort(ForgetActivity.this, "验证码已发送到您手机上");

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
                                        mBtnCheck.setBackgroundResource(R.drawable.button_bg);
                                    }
                                }));
                            }
                        }
                    });
                } else {
                    ToastUtils.showShort(ForgetActivity.this, "请正确输入手机号码");
                }
                break;

            case R.id.btn_register:
                dialog.show();

                String passWord1 = mEtPassWord1.getText().toString().trim();
                String passWord2 = mEtPassWord2.getText().toString().trim();
                String passCode = mEtCheckNumber.getText().toString().trim();

                if (!TextUtils.isEmpty(passWord1) & !TextUtils.isEmpty(passWord2)
                        & !TextUtils.isEmpty(iphone) & !TextUtils.isEmpty(passCode)) {
                    if (passWord1.equals(passWord2)) {
                        BmobUser.resetPasswordBySMSCode(passCode, passWord1, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    if (update_code != null) {
                                        BmobUser.logOut();//清除缓存用户对象
                                       // SpUtils.putBoolean(ForgetActivity.this,Constants.IS_KEEP_PASS,false);
                                        SpUtils.putBoolean(ForgetActivity.this,Constants.IS_LOGIN,false);
                                        SpUtils.putString(ForgetActivity.this,Constants.PASS_WORD,"");
                                        startActivity(new Intent(ForgetActivity.this,LoginActivity.class));
                                        dialog.hide();
                                        finish();
                                    } else {
                                        dialog.hide();
                                        finish();
                                    }
                                } else {
                                    dialog.hide();
                                    ToastUtils.showShort(ForgetActivity.this, "重置密码失败");
                                }
                            }
                        });
                    }
                } else {
                    dialog.hide();
                    ToastUtils.showShort(ForgetActivity.this, "输入框不能为空");
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

    @Override
    protected void onDestroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        SpUtils.putString(this, Constants.PASS_WORD, "");
        super.onDestroy();
    }
}
