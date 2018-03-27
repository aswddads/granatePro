package com.tjun.www.granatepro.ui.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjun.www.granatepro.R;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.tjun.www.granatepro.MyApp;
import com.tjun.www.granatepro.ui.inter.IBase;
import com.tjun.www.granatepro.utils.DialogHelper;
import com.tjun.www.granatepro.utils.StatusBarUtil;
import com.tjun.www.granatepro.utils.ToastUtils;
import com.tjun.www.granatepro.widget.MultiStateView;
import com.tjun.www.granatepro.widget.SimpleMultiStateView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;


/**
 * Created by tanjun on 2018/3/06.
 */
public abstract class BaseActivity<T1 extends BaseContract.BasePresenter> extends SupportActivity implements IBase, BaseContract.BaseView, BGASwipeBackHelper.Delegate {

//    private static final int mRequestCode = 1024;
//    private RequestPermissionCallBack mRequestPermissionCallBack;

    protected View mRootView;
    protected Dialog mLoadingDialog = null;
    Unbinder unbinder;

    @Nullable
    @BindView(R.id.SimpleMultiStateView)
    SimpleMultiStateView mSimpleMultiStateView;

    @Nullable
    @Inject
    protected T1 mPresenter;
    protected BGASwipeBackHelper mSwipeBackHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        mRootView = createView(null, null, savedInstanceState);
        setContentView(mRootView);
        initInjector(MyApp.getInstance().getApplicationComponent());
        attachView();
        bindView(mRootView, savedInstanceState);
        initStateView();
        initData();
        mLoadingDialog = DialogHelper.getLoadingDialog(this);
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(getContentLayout(), container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public View getView() {
        return mRootView;
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    private void initStateView() {
        if (mSimpleMultiStateView == null) return;
        mSimpleMultiStateView.setEmptyResource(R.layout.view_empty)
                .setRetryResource(R.layout.view_retry)
                .setLoadingResource(R.layout.view_loading)
                .setNoNetResource(R.layout.view_nonet)
                .build()
                .setonReLoadlistener(new MultiStateView.onReLoadlistener() {
                    @Override
                    public void onReload() {
                        onRetry();
                    }
                });
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。
        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(false);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    protected void setStatusBarColor(@ColorInt int color) {
        setStatusBarColor(color, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     * @param statusBarAlpha 透明度
     */
    public void setStatusBarColor(@ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        StatusBarUtil.setColorForSwipeBack(this, color, statusBarAlpha);
    }

    protected void showLoadingDialog() {
        if (mLoadingDialog != null)
            mLoadingDialog.show();
    }

    protected void showLoadingDialog(String str) {
        if (mLoadingDialog != null) {
            TextView tv = (TextView) mLoadingDialog.findViewById(R.id.tv_load_dialog);
            tv.setText(str);
            mLoadingDialog.show();
        }
    }

    protected void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    protected SimpleMultiStateView getStateView() {
        return mSimpleMultiStateView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showLoading() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showLoadingView();
        }
    }

    @Override
    public void showSuccess() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showContent();
        }
    }

    @Override
    public void showFaild() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showErrorView();
        }
    }

    @Override
    public void showNoNet() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showNoNetView();
        }
    }

    protected void T(String string) {
        ToastUtils.showShort(MyApp.getContext(), string);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }


    @Override
    public void onSwipeBackLayoutSlide(float v) {

    }

    @Override
    public void onSwipeBackLayoutCancel() {

    }

    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();

    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        boolean isAllGranted = true;
//        StringBuilder permissionName = new StringBuilder();
//        for (String s : permissions) {
//            permissionName = permissionName.append(s + "\r\n");
//        }
//
//        switch (requestCode) {
//            case mRequestCode:
//                for (int i = 0; i < grantResults.length; i++) {
//                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
//                        isAllGranted = false;
//                        //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
//                        // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
//                        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
//                            new AlertDialog.Builder(BaseActivity.this).setTitle("权限申请")//设置对话框标题
//                                    .setMessage("【用户选择了不再提示按钮，或者系统默认不在提示（如MIUI）。" +
//                                            "引导用户到应用设置页去手动授权,注意提示用户具体需要哪些权限】" +
//                                            "获取相关权限失败:" + permissionName +
//                                            "将导致部分功能无法正常使用，需要到设置页面手动授权")//设置显示的内容
//                                    .setPositiveButton("去授权", new DialogInterface.OnClickListener() {//添加确定按钮
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
//                                            //TODO Auto-generated method stub
//                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                            Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
//                                            intent.setData(uri);
//                                            startActivity(intent);
//                                            dialog.dismiss();
//                                        }
//                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {//响应事件
//                                    // TODO Auto-generated method stub
//                                    dialog.dismiss();
//                                }
//                            }).setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                @Override
//                                public void onCancel(DialogInterface dialog) {
//                                    mRequestPermissionCallBack.denied();
//                                }
//                            }).show();//在按键响应事件中显示此对话框
//                        } else {
//                            //用户拒绝权限请求，但未选中“不再提示”选项
//                            mRequestPermissionCallBack.denied();
//                        }
//                        break;
//                    }
//                }
//                if (isAllGranted) {
//                    mRequestPermissionCallBack.granted();
//                }
//        }
//    }
//
//    /**
//     * 发起权限请求
//     *
//     * @param context
//     * @param permissions
//     * @param callback
//     */
//    public void requestPermissions(final Context context, final String[] permissions,
//                                   RequestPermissionCallBack callback) {
//        this.mRequestPermissionCallBack = callback;
//        StringBuilder permissionNames = new StringBuilder();
//        for (String s : permissions) {
//            permissionNames = permissionNames.append(s + "\r\n");
//        }
//        //如果所有权限都已授权，则直接返回授权成功,只要有一项未授权，则发起权限请求
//        boolean isAllGranted = true;
//        for (String permission : permissions) {
//            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
//                isAllGranted = false;
//                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
//                    new AlertDialog.Builder(BaseActivity.this).setTitle("权限请求")//设置对话框标题
//                            .setMessage("【用户曾经拒绝过你的请求，所以这次发起请求时解释一下】" +
//                                    "您好，需要如下权限：" + permissionNames +
//                                    " 请允许，否则将影响部分功能的正常使用。")//设置显示的内容
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
//                                    //TODO Auto-generated method stub
//                                    ActivityCompat.requestPermissions(((Activity) context), permissions, mRequestCode);
//                                }
//                            }).show();//在按键响应事件中显示此对话框
//                } else {
//                    ActivityCompat.requestPermissions(((Activity) context), permissions, mRequestCode);
//                }
//                break;
//            }
//        }
//        if (isAllGranted) {
//            mRequestPermissionCallBack.granted();
//            return;
//        }
//    }
//
//    /**
//     * 动态权限回调
//     */
//    public interface RequestPermissionCallBack {
//
//        void granted();
//
//        void denied();
//    }

}
