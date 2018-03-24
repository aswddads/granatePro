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
import com.tjun.www.granatepro.bean.MyUser;
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

public class UpdateActivity extends BaseActivity {
    private Dialog dialog = null;

    @BindView(R.id.et_new_password)
    EditText mEtNewsPassWord;
    @BindView(R.id.et_again_password)
    EditText mEtAgainPassWord;
    @BindView(R.id.et_old_password)
    EditText mEtOldPassWord;

    @BindView(R.id.btn_update)
    Button mBtnUpdate;

    @BindView(R.id.tv_msg_password)
    TextView mTvMsgPassWord;

    @Override
    public int getContentLayout() {
        return R.layout.activity_update_password;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = DialogHelper.getMineLodingDiaLog(this,"重置中...");
        ToastUtils.showShort(this,"由于token原因,建议使用短信验证方式修改密码");
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

    @OnClick({R.id.btn_update,R.id.tv_msg_password})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update:
                dialog.show();

                String oldPassWord = mEtOldPassWord.getText().toString().trim();
                String newPassWord = mEtNewsPassWord.getText().toString().trim();
                String againPassWord = mEtAgainPassWord.getText().toString().trim();


                if (!TextUtils.isEmpty(oldPassWord) & !TextUtils.isEmpty(newPassWord) & !TextUtils.isEmpty(againPassWord)) {
                    if (newPassWord.equals(againPassWord)) {
                        BmobUser my = BmobUser.getCurrentUser();
                        my.setSessionToken(SpUtils.getString(UpdateActivity.this,Constants.BMOB_TOKEN,""));
                        my.updateCurrentUserPassword(oldPassWord, newPassWord, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ToastUtils.showShort(UpdateActivity.this,"修改密码成功");
                                    dialog.hide();
                                    finish();
                                } else {//旧密码方式不使用  跳转到短信验证方式
//                                    ToastUtils.showShort(UpdateActivity.this,"密码修改失败"+e.getMessage());
                                    dialog.hide();
                                    ToastUtils.showShort(UpdateActivity.this,"token失效，请使用短信验证方式修改密码");
                                    Intent intent = new Intent(UpdateActivity.this,ForgetActivity.class);
                                    intent.putExtra("this","update");
                                    startActivity(intent);
                                }
                            }
                        });
                    } else {
                        dialog.hide();
                        ToastUtils.showShort(UpdateActivity.this,"新密码不一致");
                    }
                } else {
                    dialog.hide();
                    ToastUtils.showShort(UpdateActivity.this,"输入框不能为空");
                }
                break;
            case R.id.tv_msg_password:
                Intent intent = new Intent(UpdateActivity.this,ForgetActivity.class);
                intent.putExtra("this","update");
                startActivity(intent);
                break;
        }
    }
}
