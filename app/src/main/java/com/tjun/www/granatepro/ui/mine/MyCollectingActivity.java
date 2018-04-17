package com.tjun.www.granatepro.ui.mine;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.Constants;
import com.tjun.www.granatepro.bean.db.MySelect;
import com.tjun.www.granatepro.bean.db.MyUser;
import com.tjun.www.granatepro.component.ApplicationComponent;
import com.tjun.www.granatepro.ui.base.BaseActivity;
import com.tjun.www.granatepro.ui.mine.adapter.MyAdapter;
import com.tjun.www.granatepro.ui.mine.inter.EndLessOnScrollListener;
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
import cn.bmob.v3.listener.UpdateListener;

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

    private List<MySelect> mList;

    private RecyclerView.LayoutManager mLayoutManager;
    private MyAdapter mAdapter;

    private static final int NEWS_DATA = 10;//默认加载新闻数量条数

    private Dialog dialog = null;
    private Dialog removeDialog = null;

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
        dialog = DialogHelper.getMineLodingDiaLog(this, "加载中...");
        removeDialog = DialogHelper.getMineLodingDiaLog(this, "删除中...");
        SpUtils.putBoolean(this, Constants.IS_CANCEL, false);
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!SpUtils.getBoolean(this, Constants.IS_LODING, false)) {
            dialog.show();
            SpUtils.putBoolean(this, Constants.IS_LODING, true);
        } else {
            dialog.cancel();
        }

        if (SpUtils.getBoolean(this, Constants.IS_CANCEL, false)) {
            getData();
            SpUtils.putBoolean(this, Constants.IS_CANCEL, false);
        }
        //reflesh();
        //loadeMore();

        /**
         * 下拉刷新
         */
        mSwipeMyCollect.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始转动
                mSwipeMyCollect.setRefreshing(true);
                getData();
                EndLessOnScrollListener.loading = false;  //给上拉加载更多  设置入口
                SpUtils.putInt(MyCollectingActivity.this, Constants.CURRENT_PAGE, 0);  //刷新一次，重置页码数量到最前面
            }
        });

        /**
         * 上拉加载更多
         */
        mRecycler.addOnScrollListener(new EndLessOnScrollListener((LinearLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mAdapter.showLoading();
                mAdapter.hideNoMore();
                BmobQuery<MySelect> query = new BmobQuery<>();
                MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
                //查询playerName叫“比目”的数据
                query.addWhereEqualTo("author", myUser.getObjectId());
//返回条数据，如果不加上这条语句，默认返回10条数据
                //query.setLimit(NEWS_DATA);
                query.order("-updatedAt");
                query.setSkip(NEWS_DATA * currentPage);
                query.setLimit(NEWS_DATA);
                query.findObjects(new FindListener<MySelect>() {
                    @Override
                    public void done(List<MySelect> list, BmobException e) {
                        if (e == null) {
                            mList.addAll(list);
                            mAdapter.addData(list);
                            if (list.size() == 0) {
                                mAdapter.hideLoading();
                                //mRecycler.setVisibility(View.GONE);
                                //mTvNoCollect.setVisibility(View.VISIBLE);
                                mAdapter.showNoMore();
                            } else {
                                mRecycler.setVisibility(View.VISIBLE);
                                mTvNoCollect.setVisibility(View.GONE);
                                mAdapter.hideLoading();
                                mAdapter.hideNoMore();
                                //mAdapter.notifyItemRangeInserted(mList.size()-list.size(),list.size());
                                // mRecycler.setAdapter(mAdapter);
                                onClick(mList);
                            }
                        } else {
                            ToastUtils.showShort(MyCollectingActivity.this, "我是有底线的!" + e.getMessage());
                            if (mAdapter != null) {
                                mAdapter.showNoMore();
                                mAdapter.hideLoading();
                            }
                        }
                    }
                });
            }
        });


    }


    private void getData() {

        BmobQuery<MySelect> query = new BmobQuery<>();
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
//查询playerName叫“比目”的数据
        query.addWhereEqualTo("author", myUser.getObjectId());
        //query.order("updatedAt");
//返回条数据，如果不加上这条语句，默认返回10条数据
        query.order("-updatedAt");
        query.setLimit(NEWS_DATA);

        //执行查询方法
        query.findObjects(new FindListener<MySelect>() {
            @Override
            public void done(List<MySelect> object, BmobException e) {
                if (e == null) {
                    if (dialog != null) {
                        dialog.hide();
                    }

                    if (mSwipeMyCollect.isRefreshing()) {
                        mSwipeMyCollect.setRefreshing(false);
                        if (mList.size() >= 0) {
                            mList.clear();
                            mList = new ArrayList<>(object);
                            //mList.addAll(mList);
                            if (mAdapter != null) {
                                mAdapter.updateData(mList);
                                onClick(mList);
                                //mAdapter.updateData(object);
                                //mAdapter.notifyDataSetChanged();
                                ToastUtils.showShort(MyCollectingActivity.this, "刷新成功");
                            }
                        }
                    } else {
                        mList = new ArrayList<>(object);
                        mAdapter = new MyAdapter(mList, MyCollectingActivity.this);

                        if (object.size() == 0) {
                            mRecycler.setVisibility(View.GONE);
                            mTvNoCollect.setVisibility(View.VISIBLE);
                        } else {
                            mRecycler.setVisibility(View.VISIBLE);
                            mTvNoCollect.setVisibility(View.GONE);

                            mRecycler.setAdapter(mAdapter);

                            onClick(mList);
                        }
                    }
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    ToastUtils.showShort(MyCollectingActivity.this, "可能是网络出了问题");
                }
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void reflesh() {
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
                getData();

            }
        });

//        if (SpUtils.getBoolean(MyCollectingActivity.this,Constants.IS_LOAD_MORE,false)){
//            //SpUtils.putBoolean(MyCollectingActivity.this,Constants.IS_LOAD_MORE,false);
//            loadeMore();
//        }
    }


    @Override
    public void initData() {
        mRecycler.setLayoutManager(mLayoutManager);
        //mRecycler.addItemDecoration(new SimpleDividerDecoration(this));//添加分割线
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

    /**
     * 下拉加载更多
     */
    private void loadeMore() {
        mRecycler.addOnScrollListener(new EndLessOnScrollListener((LinearLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mAdapter.showLoading();
                mAdapter.hideNoMore();
                BmobQuery<MySelect> query = new BmobQuery<>();
                MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
                //查询playerName叫“比目”的数据
                query.addWhereEqualTo("author", myUser.getObjectId());
//返回条数据，如果不加上这条语句，默认返回10条数据
                //query.setLimit(NEWS_DATA);
                query.order("-updatedAt");
                query.setSkip(NEWS_DATA * currentPage);
                query.setLimit(NEWS_DATA);
                query.findObjects(new FindListener<MySelect>() {
                    @Override
                    public void done(List<MySelect> list, BmobException e) {
                        if (e == null) {
                            mList.addAll(list);
                            mAdapter.addData(list);
                            if (list.size() == 0) {
                                mAdapter.hideLoading();
                                //mRecycler.setVisibility(View.GONE);
                                //mTvNoCollect.setVisibility(View.VISIBLE);
                                mAdapter.showNoMore();
                            } else {
                                mRecycler.setVisibility(View.VISIBLE);
                                mTvNoCollect.setVisibility(View.GONE);
                                mAdapter.hideLoading();
                                mAdapter.hideNoMore();
                                //mAdapter.notifyItemRangeInserted(mList.size()-list.size(),list.size());
                                // mRecycler.setAdapter(mAdapter);
                                onClick(mList);
                            }
                        } else {
                            ToastUtils.showShort(MyCollectingActivity.this, "我是有底线的!" + e.getMessage());
                            if (mAdapter != null) {
                                mAdapter.showNoMore();
                                mAdapter.hideLoading();
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 点击进入详情页面
     *
     * @param mySelects
     */
    private void onClick(final List<MySelect> mySelects) {
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //ToastUtils.showShort(MyCollectingActivity.this, "点击");
                //String s = mySelects.get(position).getAid();
                Intent intent = new Intent(MyCollectingActivity.this, ArticleReadActivity.class);
                intent.putExtra("aid", mySelects.get(position).getAid());
                intent.putExtra("img", mySelects.get(position).getImg());
                intent.putExtra("title", mySelects.get(position).getTitle());
                intent.putExtra("source", mySelects.get(position).getSource());
                intent.putExtra("myObjectId", mySelects.get(position).getObjectId());
                intent.putExtra("path", "collect");
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                //showSureDialog(mySelects,position);
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(MyCollectingActivity.this);
                normalDialog.setIcon(R.drawable.ic_default);
                normalDialog.setTitle("取消收藏");
                normalDialog.setMessage("取消这篇文章的收藏?");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeDialog.show();

                                MySelect mySelect = new MySelect();
                                String id = mySelects.get(position).getObjectId();
                                mySelect.setObjectId(id);
                                mySelect.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            removeDialog.cancel();
                                            mAdapter.deleteItem(position);
                                            ToastUtils.showShort(MyCollectingActivity.this, "取消收藏成功");
                                        } else {
                                            removeDialog.cancel();
                                            ToastUtils.showShort(MyCollectingActivity.this, "取消收藏失败");
                                        }
                                    }
                                });
                            }
                        });
                normalDialog.setNegativeButton("关闭",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeDialog.hide();
                            }
                        });
                // 显示
                normalDialog.show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpUtils.putBoolean(this, Constants.IS_LODING, false);
        SpUtils.putInt(this, Constants.CURRENT_PAGE, 0);
    }

    /**
     * 对话框
     */
    private void showSureDialog(final List<MySelect> mySelects, final int position){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */

    }
}
