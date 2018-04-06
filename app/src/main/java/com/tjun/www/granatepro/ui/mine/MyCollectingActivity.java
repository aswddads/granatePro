package com.tjun.www.granatepro.ui.mine;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.Constants;
import com.tjun.www.granatepro.bean.MySelect;
import com.tjun.www.granatepro.bean.MyUser;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseActivity;
import com.tjun.www.granatepro.ui.mine.adapter.MyAdapter;
import com.tjun.www.granatepro.ui.news.ArticleReadActivity;
import com.tjun.www.granatepro.utils.DialogHelper;
import com.tjun.www.granatepro.utils.SpUtils;
import com.tjun.www.granatepro.utils.ToastUtils;
import com.tjun.www.granatepro.widget.SimpleDividerDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by tanjun on 2018/3/27.
 */

public class MyCollectingActivity extends BaseActivity {

    @BindView(R.id.tv_back)
    TextView mIvBack;
    @BindView(R.id.recycler_my_collect)
    RecyclerView mRecycler;

    @BindView(R.id.swipe_my_collect)
    SwipeRefreshLayout mSwipeMyCollect;

    @BindView(R.id.tv_no_collect)
    TextView mTvNoCollect;

    private RecyclerView.LayoutManager mLayoutManager;
    private MyAdapter mAdapter;

    private Dialog dialog = null;

    private List<MySelect> mList;

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_collect;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = DialogHelper.getMineLodingDiaLog(this,"加载中");
        SpUtils.putBoolean(this,Constants.IS_CANCEL,false);
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!SpUtils.getBoolean(this,Constants.IS_LODING,false)) {
            dialog.show();
            SpUtils.putBoolean(this,Constants.IS_LODING,true);
        } else {
            dialog.cancel();
        }

        if (SpUtils.getBoolean(this,Constants.IS_CANCEL,false)) {
            getData();
            SpUtils.putBoolean(this,Constants.IS_CANCEL,false);
        }
    }


    private void getData() {

        BmobQuery<MySelect> query = new BmobQuery<>();
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
//查询playerName叫“比目”的数据
        query.addWhereEqualTo("author", myUser.getObjectId());
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(10);

        //执行查询方法
        query.findObjects(new FindListener<MySelect>() {
            @Override
            public void done(List<MySelect> object, BmobException e) {
                if (e == null) {
                    dialog.hide();

                    mList = new ArrayList<>();

                    mList.addAll(object);

                    mAdapter = new MyAdapter(mList, MyCollectingActivity.this);

                    if (object.size() == 0) {
                        mRecycler.setVisibility(View.GONE);
                        mTvNoCollect.setVisibility(View.VISIBLE);
                    } else {
                        mRecycler.setVisibility(View.VISIBLE);
                        mTvNoCollect.setVisibility(View.GONE);
                        mRecycler.setAdapter(mAdapter);

                        onClick(object);

                    }

                    reflesh(mAdapter);
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    ToastUtils.showShort(MyCollectingActivity.this, "可能是网络出了问题");
                }
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void reflesh(final MyAdapter mAdapter) {
//        mSwipeMyCollect.setColorSchemeColors(
//                android.R.color.holo_blue_dark,
//                android.R.color.holo_blue_light,
//                android.R.color.holo_green_light,
//                android.R.color.holo_green_light);

        mSwipeMyCollect.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始转动
                mSwipeMyCollect.setRefreshing(true);


                BmobQuery<MySelect> query = new BmobQuery<>();
                MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
//查询playerName叫“比目”的数据
                query.addWhereEqualTo("author", myUser.getObjectId());
//返回50条数据，如果不加上这条语句，默认返回10条数据
                query.setLimit(10);

                query.findObjects(new FindListener<MySelect>() {
                    @Override
                    public void done(List<MySelect> list, BmobException e) {
                        if (e == null) {
                            mSwipeMyCollect.setRefreshing(false);
                            ToastUtils.showShort(MyCollectingActivity.this,"刷新成功");
                            if (list.size() == 0){
                                mRecycler.setVisibility(View.GONE);
                                mTvNoCollect.setVisibility(View.VISIBLE);
                            } else {
                                mRecycler.setVisibility(View.VISIBLE);
                                mTvNoCollect.setVisibility(View.GONE);
                                //mList.addAll(list);
                                mList = list;
                                mAdapter.updateData(mList);
                                onClick(mList);
                            }

                        } else {
                            ToastUtils.showShort(MyCollectingActivity.this, "刷新失败");
                        }
                    }
                });
            }
        });
    }


    @Override
    public void initData() {
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.addItemDecoration(new SimpleDividerDecoration(this));//添加分割线
        // 设置Item添加和移除的动画
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        //mRecycler.setAdapter(mAdapter);

    }

    @Override
    public void onRetry() {

    }

    @OnClick(R.id.tv_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }

    private void onClick(final List<MySelect> mySelects) {
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //ToastUtils.showShort(MyCollectingActivity.this, "点击");
                //String s = mySelects.get(position).getAid();
                Intent intent = new Intent(MyCollectingActivity.this, ArticleReadActivity.class);
                intent.putExtra("aid",mySelects.get(position).getAid());
                intent.putExtra("img",mySelects.get(position).getImg());
                intent.putExtra("title",mySelects.get(position).getTitle());
                intent.putExtra("source",mySelects.get(position).getSource());
                intent.putExtra("myObjectId",mySelects.get(position).getObjectId());
                intent.putExtra("path","collect");
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpUtils.putBoolean(this, Constants.IS_LODING,false);
    }
}
