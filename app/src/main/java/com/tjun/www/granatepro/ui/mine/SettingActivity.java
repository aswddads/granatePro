package com.tjun.www.granatepro.ui.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.Constants;
import com.tjun.www.granatepro.bean.db.MyUser;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseActivity;
import com.tjun.www.granatepro.utils.DialogHelper;
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

public class SettingActivity extends BaseActivity {
    private Dialog dialog = null;

    @BindView(R.id.et_update_username)
    EditText mEtUpdateUserName;

    @BindView(R.id.et_desc)
    EditText mEtDes;

    @BindView(R.id.btn_update)
    Button mBtnUpdate;

    @BindView(R.id.tv_out)
    TextView mTvOut;

    @BindView(R.id.tv_update_password)
    TextView mTvUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = DialogHelper.getMineLodingDiaLog(this,"修改中...");
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        if (SpUtils.getBoolean(this, Constants.IS_LOGIN, false)) {
            MyUser myUser = BmobUser.getCurrentUser(MyUser.class);

            mEtUpdateUserName.setText(myUser.getUsername());
            mEtDes.setText(myUser.getDesc());
        }
    }

    @Override
    public void initData() {
    }

    @Override
    public void onRetry() {

    }

    @OnClick({R.id.btn_update, R.id.tv_out,R.id.tv_update_password,R.id.tv_back})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update:
                //获取当前用户
                dialog.show();
                final MyUser myUser = BmobUser.getCurrentUser(MyUser.class);

                String username = mEtUpdateUserName.getText().toString().trim();
//                String newsPassWord = mEtNewsPassWord.getText().toString().trim();
//                String againPassword = mEtAgainPassWord.getText().toString().trim();
//                String oldPassWord = mEtOldPassWord.getText().toString().trim();
                String desc = mEtDes.getText().toString().trim();

//                if (newsPassWord != null && againPassword != null && newsPassWord.length() > 0 && againPassword.length() > 0) {
////                    if (newsPassWord == againPassword) {
////                        myUser.setPassword(newsPassWord);
////                    } else {
////                        ToastUtils.showShort(this,"两次密码不一致，请再次确认输入");
////                        return;
////                    }
//
//                    if (newsPassWord.equals(againPassword)) {//修改密码逻辑
//                        BmobUser.updateCurrentUserPassword(oldPassWord, againPassword, new UpdateListener() {
//                            @Override
//                            public void done(BmobException e) {
//                                if (e != null) {
//                                    ToastUtils.showShort(SettingActivity.this, "旧密码输入错误" + e.toString());
//                                    return;
//                                }
//                            }
//                        });
//                    } else {
//                        ToastUtils.showShort(this, "两次密码不一致，请再次确认输入");
//                        return;
//                    }
//                }

                if (!TextUtils.isEmpty(username) && !username.equals(myUser.getUsername())) {
                    myUser.setUsername(username);
                }

                if (!TextUtils.isEmpty(desc) && !desc.equals(myUser.getDesc())) {
                    myUser.setDesc(desc);
                }
                myUser.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            //  回调
                            Intent intent = new Intent();
                            intent.putExtra("username", myUser.getUsername());
                            intent.putExtra("desc", myUser.getDesc());
                            setResult(RESULT_OK, intent);
                            dialog.dismiss();
                            ToastUtils.showShort(SettingActivity.this, "修改成功");
                            finish();
                        } else {
                            dialog.dismiss();
                            ToastUtils.showShort(SettingActivity.this, "修改失败" + e.toString());
                        }
                    }
                });

                break;

            case R.id.tv_back:
                finish();
                break;

            case R.id.tv_out://退出登录
                BmobUser.logOut();//清除缓存用户对象
                SpUtils.putBoolean(this,Constants.IS_LOGIN,false);
                setResult(RESULT_OK);
                finish();
                break;

            case R.id.tv_update_password://修改密码
                startActivity(new Intent(SettingActivity.this,UpdateActivity.class));
                finish();
                break;
        }
    }
}
