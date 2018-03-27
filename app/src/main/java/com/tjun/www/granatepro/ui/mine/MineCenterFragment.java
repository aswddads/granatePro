package com.tjun.www.granatepro.ui.mine;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.Constants;
import com.tjun.www.granatepro.bean.MyUser;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseFragment;
import com.tjun.www.granatepro.utils.ContextUtils;
import com.tjun.www.granatepro.utils.PhotoUtils;
import com.tjun.www.granatepro.utils.SpUtils;
import com.tjun.www.granatepro.utils.StatusBarUtil;
import com.tjun.www.granatepro.utils.ToastUtils;
import com.tjun.www.granatepro.widget.CustomDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tanjun on 2018/3/16.
 */

public class MineCenterFragment extends BaseFragment {

    private static final int OPEN_REGISTER = 1000;
    private static final int OPEN_SETTING = 1001;
    private static final int CAMERA_REQUEST_CODE = 1002;
    private static final int IMAGE_REQUEST_CODE = 1003;
    private static final int RESULT_REQUEST_CODE = 1004;

    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");

    private Uri imageUri;
    private Uri cropImageUri;

    private File tempFile = null;

    private CustomDialog dialog = null;

    @BindView(R.id.fl_top_login_parent)
    FrameLayout mFlTopLoginParent;

    @BindView(R.id.rl_un_login)
    RelativeLayout mRlUnLogin;

    @BindView(R.id.ll_login)
    LinearLayout mllLogin;

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

    @BindView(R.id.circleImageView)
    CircleImageView mCircleImageView;

    @BindView(R.id.fake_status_bar)
    View mView;

    private Button mBtnCamera;
    private Button mBtnPicture;
    private Button mBtnCancel;

    public static MineCenterFragment newInstance() {
        Bundle args = new Bundle();
        MineCenterFragment fragment = new MineCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new CustomDialog(getActivity(), 0, 0, R.layout.dialog_photo, R.style.pop_anim_style, Gravity.BOTTOM, 0);
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!SpUtils.getBoolean(getActivity(),Constants.IS_LOGIN,false)) {
            if (mView.getVisibility() == View.GONE) {
                mView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        //mView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mBtnCamera = (Button) dialog.findViewById(R.id.btn_camera);
        mBtnPicture = (Button) dialog.findViewById(R.id.btn_picture);
        mBtnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        if (!SpUtils.getBoolean(getContext(), Constants.IS_LOGIN, false)) {
            mRlUnLogin.setVisibility(View.VISIBLE);
            mllLogin.setVisibility(View.GONE);
            mView.setVisibility(View.VISIBLE);
        } else {
            mRlUnLogin.setVisibility(View.GONE);
            mllLogin.setVisibility(View.VISIBLE);
            mView.setVisibility(View.GONE);
            MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
            mTvLogin.setText(myUser.getUsername());
            mTvDes.setText(myUser.getDesc());
            //SpUtils.getImageFromSp(getActivity(),mCircleImageView);
            if (getBitmapFromString(myUser) != null && myUser.getHeadImg() != null) {
                mCircleImageView.setImageBitmap(getBitmapFromString(myUser));
                //mCircleImageView.setImageDrawable(R.drawable.ic_default);
            }

            if (getBitmapFromString(myUser) != null && myUser.getHeadImg() != null) {
                Bitmap bitmap = getBitmapFromString(myUser.getBackgroundImg());
                mllLogin.setBackground(new BitmapDrawable(bitmap));
            }
        }
    }

    private Bitmap getBitmapFromString(MyUser myUser){

        String imageString = myUser.getHeadImg();
        if (imageString != null) {
            byte[] byteArray = Base64.decode(imageString,Base64.DEFAULT);
            ByteArrayInputStream byInputStream = new ByteArrayInputStream(byteArray);
            Bitmap bitmap = BitmapFactory.decodeStream(byInputStream);
           // imageView.setImageBitmap(bitmap);
            return bitmap;
        } else {
            return null;
        }

    }

    private Bitmap getBitmapFromString(String imgString){
        if (imgString != null) {
            byte[] byteArray = Base64.decode(imgString,Base64.DEFAULT);
            ByteArrayInputStream byInputStream = new ByteArrayInputStream(byteArray);
            Bitmap bitmap = BitmapFactory.decodeStream(byInputStream);
            // imageView.setImageBitmap(bitmap);
            return bitmap;
        } else {
            return null;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        mBtnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toPicture();
                dialog.dismiss();
                requestPermissions(getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new RequestPermissionCallBack() {
                    @Override
                    public void granted() {
                        PhotoUtils.toPicture(MineCenterFragment.this, IMAGE_REQUEST_CODE);
                    }

                    @Override
                    public void denied() {
                        ToastUtils.showShort(getActivity(), "部分权限获取失败，正常功能受到影响");

                    }
                });
//                requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new RequestPermissionCallBack()
//                {
//                    @Override
//                    public void granted() {
//                        PhotoUtils.toPicture(getActivity(), IMAGE_REQUEST_CODE);
//                    }
//
//                    @Override
//                    public void denied() {
//                        ToastUtils.showShort(getActivity(), "部分权限获取失败，正常功能受到影响");
//                    }
//                });
            }
        });

        mBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // toCamera();
                requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, new RequestPermissionCallBack() {
                    @Override
                    public void granted() {
                        if (hasSdCard()) {
                            imageUri = Uri.fromFile(fileUri);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                                //通过FileProvider创建一个content类型的Uri
                                imageUri = FileProvider.getUriForFile(getContext(),  "com.tjun.www.granatepro.fileprovider", fileUri);
                            PhotoUtils.toCamera(MineCenterFragment.this, imageUri, CAMERA_REQUEST_CODE);
                        } else {
                            ToastUtils.showShort(getContext(), "设备没有SD卡！");
//                            Log.e("asd", "设备没有SD卡");
                        }
                    }

                    @Override
                    public void denied() {
                        ToastUtils.showShort(getActivity(), "部分权限获取失败，正常功能受到影响");
                    }
                });
            }
        });
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.rl_un_login, R.id.tv_des, R.id.tv_my_collect, R.id.tv_my_settinggs, R.id.copy_right, R.id.circleImageView
    ,R.id.tv_change_background})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.copy_right:
                toWeb(getResources().getString(R.string.copyright));
                break;
            case R.id.rl_un_login:
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), OPEN_REGISTER);
                //跳转登录界面
                break;
            case R.id.tv_my_collect:
                //跳转新闻收藏页面
                break;
            case R.id.tv_my_settinggs:
                //跳转设置界面
                if (SpUtils.getBoolean(getContext(), Constants.IS_LOGIN, false)) {
                    startActivityForResult(new Intent(getActivity(), SettingActivity.class), OPEN_SETTING);
                } else {
                    ToastUtils.showShort(getContext(), "请先登录");
                }
                break;

            case R.id.circleImageView:// 用户头像   true
                SpUtils.putBoolean(getActivity(),Constants.IS_HEAD_IMG,true);
                dialog.show();
                break;
            case R.id.tv_change_background://切换背景  false
                SpUtils.putBoolean(getActivity(),Constants.IS_HEAD_IMG,false);
                dialog.show();
                break;
        }
    }

//    /**
//     * 相册
//     */
//    private void toPicture() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //跳转图库
//        intent.setType("image/*");//全部图片
//        startActivityForResult(intent, IMAGE_REQUEST_CODE);
//        dialog.dismiss();
//    }

//    /**
//     * 相机
//     */
//    private void toCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        //判断内存卡是否可用
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // Android 7.0+
//            Uri uri = FileProvider.getUriForFile(getActivity(), getContext().getPackageName() + ".fileprovider", new File(Environment
//                    .getExternalStorageDirectory(), "fileImg.jpg"));//通过FileProvider创建一个content类型的Uri
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
////            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//将拍取的照片保存到指定URI
//
//            startActivityForResult(intent, CAMERA_REQUEST_CODE);
//
//        } else {
//            Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "fileImg.jpg"));//7.0以下打开相机拍照的方法
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//保存照片
//            startActivityForResult(intent, CAMERA_REQUEST_CODE);
//        }

//        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(
//                new File(Environment.getExternalStorageDirectory(),Constants.PHOTO_IMAGE_FILE_NAME)));
//        startActivityForResult(intent,CAMERA_REQUEST_CODE);
//        dialog.dismiss();
//    }

    private void toWeb(String url) {
        Uri weburl = Uri.parse(url);
        Intent web_Intent = new Intent(Intent.ACTION_VIEW, weburl);
        getActivity().startActivity(web_Intent);
    }

    /**
     * 登录成功回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else {
            if (requestCode == OPEN_REGISTER && SpUtils.getBoolean(getContext(), Constants.IS_LOGIN, false)) {

                mRlUnLogin.setVisibility(View.GONE);
                mllLogin.setVisibility(View.VISIBLE);
                mView.setVisibility(View.GONE);

                mTvLogin.setText(data.getStringExtra("username"));
                mTvDes.setText(data.getStringExtra("desc"));
                if (data.getStringExtra("img") != null){
                    mCircleImageView.setImageBitmap(getBitmapFromString(data.getStringExtra("img")));
                }

                Bitmap bitmap = getBitmapFromString(data.getStringExtra("backImg"));
                if (bitmap != null) {
                    mllLogin.setBackground(new BitmapDrawable(bitmap));
                }
            } else if (requestCode == OPEN_SETTING && !SpUtils.getBoolean(getContext(), Constants.IS_LOGIN, false)) {
                mRlUnLogin.setVisibility(View.VISIBLE);
                mllLogin.setVisibility(View.GONE);

                mTvDes.setText("程序员 Asw.Tan");
            } else if (requestCode == OPEN_SETTING && SpUtils.getBoolean(getContext(), Constants.IS_LOGIN, false)) {
                mRlUnLogin.setVisibility(View.GONE);
                mllLogin.setVisibility(View.VISIBLE);

                mTvLogin.setText(data.getStringExtra("username"));
                mTvDes.setText(data.getStringExtra("desc"));
            } else if (requestCode == CAMERA_REQUEST_CODE) {
                cropImageUri = Uri.fromFile(fileCropUri);
                if (SpUtils.getBoolean(getActivity(),Constants.IS_HEAD_IMG,false)){
                    PhotoUtils.cropImageUri(MineCenterFragment.this, imageUri, cropImageUri, 1, 1,
                            150, 150, RESULT_REQUEST_CODE);
                } else {
                    PhotoUtils.cropImageUri(MineCenterFragment.this, imageUri, cropImageUri, 1, 1,
                            ContextUtils.getSreenWidth(getActivity()), 280, RESULT_REQUEST_CODE);
                }

            } else if (requestCode == IMAGE_REQUEST_CODE) {
                if (hasSdCard()) {
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Uri newUri = Uri.parse(PhotoUtils.getPath(getActivity(), data.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        newUri = FileProvider.getUriForFile(getActivity(),  "com.tjun.www.granatepro.fileprovider", new File(newUri.getPath()));
                    if (SpUtils.getBoolean(getActivity(),Constants.IS_HEAD_IMG,false)) {
                        PhotoUtils.cropImageUri(MineCenterFragment.this, newUri, cropImageUri, 1, 1,
                                150, 150, RESULT_REQUEST_CODE);
                    } else {
                        PhotoUtils.cropImageUri(MineCenterFragment.this, newUri, cropImageUri, 1, 1,
                                ContextUtils.getSreenWidth(getActivity()), 280, RESULT_REQUEST_CODE);
                    }
                } else {
                    ToastUtils.showShort(getActivity(), "设备没有SD卡!");
                }

            } else if (requestCode == RESULT_REQUEST_CODE) {

                Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, getActivity());

                //有可能取消
                if (bitmap != null) {

                    if (SpUtils.getBoolean(getActivity(),Constants.IS_HEAD_IMG,false)){
                        showImages(bitmap);
                        //添加头像信息数据到数据库

                        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
                        myUser.setHeadImg(getImgString(bitmap));

                        String objectId = myUser.getObjectId();

                        myUser.update(objectId, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null){
                                   // ToastUtils.showShort(getActivity(),"头像信息同步成功");
                                } else {
                                    ToastUtils.showShort(getActivity(),"头像信息同步到远端失败");
                                }
                            }
                        });
                    } else {
                        mllLogin.setBackground(new BitmapDrawable(bitmap));
                        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
                        myUser.setBackgroundImg(getImgString(bitmap));
                        String objectId = myUser.getObjectId();

                        myUser.update(objectId, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null){
                                   // ToastUtils.showShort(getActivity(),"背景信息同步成功");
                                } else {
                                    ToastUtils.showShort(getActivity(),"背景信息同步到远端失败");
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    /**
     * 拿到bitmap压缩为String
     * @param bitmap
     * @return
     */
    private String getImgString(Bitmap bitmap) {
        //bitmap压缩成字节数组输出
        ByteArrayOutputStream byStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
        //利用base64将字节数组输出流转化为String
        byte[] byteArray = byStream.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArray,Base64.DEFAULT));
        return imageString;
    }


    private void showImages(Bitmap bitmap) {
        mCircleImageView.setImageBitmap(bitmap);
    }

//    /**
//     * 裁剪
//     *
//     * @param uri
//     */
//    private void startPhotoZoom(Uri uri) {
//        if (uri == null)
//            return;
//        Intent intent = new Intent("com.android.camera.action.CROP");//隐式跳转
//        intent.setDataAndType(uri, "image/*");
//        //设置裁剪
//        intent.putExtra("crop", "true");
//        // 裁剪宽高比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        //裁剪图片质量
//        intent.putExtra("outputX", 320);
//        intent.putExtra("outputY", 320);
//        //发送数据
//        intent.putExtra("return-data", "true");
//
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//只是相应的赋予的是写权限
//
//        startActivityForResult(intent, RESULT_REQUEST_CODE);
//    }

//    private void setImageView(Intent data) {
//        Bundle bundle = data.getExtras();
//        if (bundle != null) {
//            Bitmap bitmap = bundle.getParcelable("data");
//            mCircleImageView.setImageBitmap(bitmap);
//        }
//    }


    @Override
    public void onStop() {
        super.onStop();
        SpUtils.putImageToSp(getActivity(), mCircleImageView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    private boolean hasSdCard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

}
