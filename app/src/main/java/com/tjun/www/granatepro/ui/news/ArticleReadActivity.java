package com.tjun.www.granatepro.ui.news;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.Constants;
import com.tjun.www.granatepro.bean.MyCollectNewsBeam;
import com.tjun.www.granatepro.bean.MySelect;
import com.tjun.www.granatepro.bean.MyUser;
import com.tjun.www.granatepro.component.DaggerHttpComponent;
import com.tjun.www.granatepro.bean.NewsArticleBean;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseActivity;
import com.tjun.www.granatepro.ui.mine.MyCollectingActivity;
import com.tjun.www.granatepro.ui.news.contract.ArticleReadContract;
import com.tjun.www.granatepro.ui.news.presenter.ArticleReadPresenter;
import com.tjun.www.granatepro.utils.DateUtil;
import com.tjun.www.granatepro.utils.SpUtils;
import com.tjun.www.granatepro.utils.ToastUtils;
import com.tjun.www.granatepro.widget.ObservableScrollView;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by tanjun on 2018/3/15.
 */
public class ArticleReadActivity extends BaseActivity<ArticleReadPresenter> implements ArticleReadContract.View {
    private static final String TAG = "ArticleReadActivity";
    private static final int MAX_COLLECT = 200;

    private String id;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_updateTime)
    TextView mTvUpdateTime;
    //    @BindView(R.id.tv_content)
//    TextView mTvContent;
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.ScrollView)
    ObservableScrollView mScrollView;
    @BindView(R.id.ConstraintLayout)
    RelativeLayout mConstraintLayout;
    @BindView(R.id.rl_top)
    RelativeLayout mRlTop;
    @BindView(R.id.iv_topLogo)
    ImageView mIvTopLogo;
    @BindView(R.id.tv_topname)
    TextView mTvTopName;
    @BindView(R.id.tv_TopUpdateTime)
    TextView mTvTopUpdateTime;

    @BindView(R.id.btn_like)
    Button mBtnLike;
    @BindView(R.id.btn_unlike)
    Button mBtnUnLike;

    private String imgString;//item img url
    private String mAid;
    private String mTitle;
    private String mSource;

    private NewsArticleBean mNews;

    @Override
    public int getContentLayout() {
        return R.layout.activity_artcleread;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgString = getIntent().getStringExtra("img");
        mAid = getIntent().getStringExtra("aid");
        mTitle = getIntent().getStringExtra("title");
        mSource = getIntent().getStringExtra("source");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBtnLike.getVisibility() == View.VISIBLE & SpUtils.getBoolean(this, Constants.IS_LOGIN, false)) {
            //添加一个查询
            MyUser myUser = BmobUser.getCurrentUser(MyUser.class);

            BmobQuery<MySelect> query = new BmobQuery<>();
            query.addWhereEqualTo("img", imgString);
            query.addWhereEqualTo("author",myUser.getObjectId());
            query.setLimit(1);
            query.findObjects(new FindListener<MySelect>() {
                @Override
                public void done(List<MySelect> object, BmobException e) {
                    if (e == null) {// 查询成功

                        id = object.get(0).getObjectId();

                        if (id != null) {
                            mBtnLike.setVisibility(View.GONE);
                            mBtnUnLike.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });
        }
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        setWebViewSetting();
        setStatusBarColor(Color.parseColor("#BDBDBD"), 30);

        mScrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int scrollY, int oldx, int oldy) {
                if (scrollY > mConstraintLayout.getHeight()) {
                    mRlTop.setVisibility(View.VISIBLE);
                } else {
                    mRlTop.setVisibility(View.GONE);

                }
            }
        });
    }

    private void setWebViewSetting() {
        addjs(mWebView);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setVerticalScrollbarOverlay(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setHorizontalScrollbarOverlay(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.loadUrl("file:///android_asset/ifeng/post_detail.html");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String aid = getIntent().getStringExtra("aid");
                mPresenter.getData(aid);
            }
        });
    }

    @Override
    public void initData() {
    }

    @Override
    public void onRetry() {
        String aid = getIntent().getStringExtra("aid");
        mPresenter.getData(aid);
    }

    @Override
    public void loadData(final NewsArticleBean articleBean) {

        mNews = articleBean;

        mTvTitle.setText(articleBean.getBody().getTitle());
        mTvUpdateTime.setText(DateUtil.getTimestampString(DateUtil.string2Date(articleBean.getBody().getUpdateTime(), "yyyy/MM/dd HH:mm:ss")));
        if (articleBean.getBody().getSubscribe() != null) {
            Glide.with(this).load(articleBean.getBody().getSubscribe().getLogo())
                    .apply(new RequestOptions()
                            .transform(new CircleCrop())
                            //.placeholder()
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(mIvLogo);
            Glide.with(this).load(articleBean.getBody().getSubscribe().getLogo())
                    .apply(new RequestOptions()
                            .transform(new CircleCrop())
                            //.placeholder()
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(mIvTopLogo);
            mTvTopName.setText(articleBean.getBody().getSubscribe().getCateSource());
            mTvName.setText(articleBean.getBody().getSubscribe().getCateSource());
            mTvTopUpdateTime.setText(articleBean.getBody().getSubscribe().getCatename());
        } else {
            mTvTopName.setText(articleBean.getBody().getSource());
            mTvName.setText(articleBean.getBody().getSource());
            mTvTopUpdateTime.setText(!TextUtils.isEmpty(articleBean.getBody().getAuthor()) ? articleBean.getBody().getAuthor() : articleBean.getBody().getEditorcode());
        }
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                final String content = articleBean.getBody().getText();
                String url = "javascript:show_content(\'" + content + "\')";
                mWebView.loadUrl(url);
                showSuccess();
            }
        });
    }


    private void addjs(final WebView webview) {

        class JsObject {
            @JavascriptInterface
            public void jsFunctionimg(final String i) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run: " + i);
                    }
                });

            }

        }
        webview.addJavascriptInterface(new JsObject(), "jscontrolimg");

    }

    @OnClick({R.id.iv_back, R.id.btn_like, R.id.btn_unlike})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_like:
                if (SpUtils.getBoolean(this, Constants.IS_LOGIN, false)) {
                    check();
                } else {
                    ToastUtils.showShort(this, "用户需要先登录才能进行文章收藏");
                }
                break;

            case R.id.btn_unlike:
                if (SpUtils.getBoolean(this, Constants.IS_LOGIN, false)) {
                    delete();
                } else {
                    ToastUtils.showShort(this, "用户需要先登录才能取消文章收藏");
                }

//                BmobQuery<MySelect> query = new BmobQuery<MySelect>();
//                MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
//                //String [] hobby = {"阅读","唱歌"};
//                query.addWhereEqualTo("author", myUser);
//                query.findObjects(new FindListener<MySelect>() {
//
//                    @Override
//                    public void done(List<MySelect> object,BmobException e) {
//                        if(e==null){
//                            String s = object.get(2).getNews().get(0).getImg();
//                            ToastUtils.showShort(ArticleReadActivity.this,"数据:"+s);
//                        }else{
//                            Log.i("bmob","失败："+e.getMessage());
//                        }
//                    }
//
//                });

//            case R.id.bt_like:
//                add();
//                break;
        }
    }

    private void delete() {

        MySelect mySelect = new MySelect();



        mySelect.setObjectId(id);



        mySelect.delete(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    mBtnLike.setVisibility(View.VISIBLE);
                    mBtnUnLike.setVisibility(View.GONE);
                    //SpUtils.putBoolean(ArticleReadActivity.this, Constants.IS_ADD_NEW, false);
                    ToastUtils.showShort(ArticleReadActivity.this, "取消收藏成功");
                } else {
                    ToastUtils.showShort(ArticleReadActivity.this, "取消收藏失败" + e.getMessage());
                }
            }
        });
    }


    private void check() {
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<MySelect> query = new BmobQuery<MySelect>();
        query.addWhereEqualTo("author",myUser.getObjectId());
        query.count(MySelect.class, new CountListener() {
            @Override
            public void done(Integer count, BmobException e) {
                if(e==null){
                    if (count < MAX_COLLECT) { //允许收藏的新闻最大数为100
                        add();
                    } else {
                        ToastUtils.showLong(ArticleReadActivity.this,"允许收藏最大新闻数量为100,您当前收藏了"+count+"篇新闻,请先进行删除相应新闻再收藏");
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void add() {


        MySelect mySelect = new MySelect();

        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);

        mySelect.setAuthor(myUser);
        mySelect.setImg(imgString);
        mySelect.setAid(mAid);
        mySelect.setTitle(mTitle);
        mySelect.setSource(mSource);

        mySelect.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    mBtnLike.setVisibility(View.GONE);
                    mBtnUnLike.setVisibility(View.VISIBLE);
                    //pUtils.putBoolean(ArticleReadActivity.this, Constants.IS_ADD_NEW, true);
                    id = s;
                    ToastUtils.showShort(ArticleReadActivity.this, "收藏成功");
                } else {
                    ToastUtils.showShort(ArticleReadActivity.this, "收藏失败" + e.getMessage());
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpUtils.putBoolean(this,Constants.IS_LODING,true);
    }
}


