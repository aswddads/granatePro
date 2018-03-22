package com.tjun.www.granatepro.ui.mine;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.Constants;
import com.tjun.www.granatepro.bean.MyUser;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseFragment;
import com.tjun.www.granatepro.utils.SpUtils;
import com.tjun.www.granatepro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;

/**
 * Created by tanjun on 2018/3/16.
 */

public class MineCenterFragment extends BaseFragment{

    private static final int OPEN_REGISTER = 1000;
    private static final int OPEN_SETTING = 1001;

    @BindView(R.id.fl_top_login_parent)
    FrameLayout mFlTopLoginParent;

    @BindView(R.id.rl_un_login)
    RelativeLayout mRlUnLogin;

    @BindView(R.id.rl_login)
    RelativeLayout mRlLogin;
    @BindView(R.id.iv_login)
    ImageView mIvLogin;
    @BindView(R.id.tv_login)
    TextView mTvLogin;

    @BindView(R.id.tv_des)
    TextView mTvDes;

    @BindView(R.id.tv_my_collect)
    TextView mTvMyCollect;

    @BindView(R.id.tv_my_settinggs)
    TextView mTvMySettings;

    @BindView(R.id.copy_right)
    TextView mCopyRight;

    @BindView(R.id.g_3)
    View mView;

    public static MineCenterFragment newInstance(){
        Bundle args = new Bundle();
        MineCenterFragment fragment = new MineCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getContentLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        mView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        if (!SpUtils.getBoolean(getContext(), Constants.IS_LOGIN,false)) {
            mRlUnLogin.setVisibility(View.VISIBLE);
            mRlLogin.setVisibility(View.GONE);
        } else {
            mRlUnLogin.setVisibility(View.GONE);
            mRlLogin.setVisibility(View.VISIBLE);
            MyUser myUser = BmobUser.getCurrentUser(MyUser.class);

            mTvLogin.setText(myUser.getUsername());
            mTvDes.setText(myUser.getDesc());
        }
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.rl_un_login, R.id.tv_des,R.id.tv_my_collect,R.id.tv_my_settinggs,R.id.copy_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.copy_right:
                toWeb(getResources().getString(R.string.copyright));
                break;
            case R.id.rl_un_login:
                startActivityForResult(new Intent(getActivity(),LoginActivity.class),OPEN_REGISTER);
                //跳转登录界面
                break;
            case R.id.tv_my_collect:
                //跳转新闻收藏页面
                break;
            case R.id.tv_my_settinggs:
                //跳转设置界面
                if (SpUtils.getBoolean(getContext(),Constants.IS_LOGIN,false)) {
                    startActivityForResult(new Intent(getActivity(),SettingActivity.class),OPEN_SETTING);
                } else {
                    ToastUtils.showShort(getContext(),"请先登录");
                }
                break;

        }
    }

    private void toWeb(String url){
        Uri weburl = Uri.parse(url);
        Intent web_Intent = new Intent(Intent.ACTION_VIEW, weburl);
        getActivity().startActivity(web_Intent);
    }

    /**
     * 登录成功回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == OPEN_REGISTER && SpUtils.getBoolean(getContext(),Constants.IS_LOGIN,false)) {
            mRlUnLogin.setVisibility(View.GONE);
            mRlLogin.setVisibility(View.VISIBLE);

            mTvLogin.setText(data.getStringExtra("username"));
            mTvDes.setText(data.getStringExtra("desc"));
        }

        else if (requestCode == OPEN_SETTING && !SpUtils.getBoolean(getContext(),Constants.IS_LOGIN,false)) {
            mRlUnLogin.setVisibility(View.VISIBLE);
            mRlLogin.setVisibility(View.GONE);

            mTvDes.setText("程序员 Asw.Tan");
        }

        else if (requestCode == OPEN_SETTING && SpUtils.getBoolean(getContext(),Constants.IS_LOGIN,false)) {
            mRlUnLogin.setVisibility(View.GONE);
            mRlLogin.setVisibility(View.VISIBLE);

            mTvLogin.setText(data.getStringExtra("username"));
            mTvDes.setText(data.getStringExtra("desc"));
        }
    }
}
