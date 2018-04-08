package com.tjun.www.granatepro.ui.mine.inter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tjun.www.granatepro.bean.Constants;
import com.tjun.www.granatepro.utils.SpUtils;

/**
 * Created by tanjun on 2018/4/3.
 */

public abstract class EndLessOnScrollListener extends RecyclerView.OnScrollListener{

    private LinearLayoutManager mLinearLayoutManager;

    //已经加载出来的Item的数量
    private int totalItemCount;

    //当前页，从0开始
    private int currentPage = 0;

    //主要用来存储上一个totalItemCount
    private int previousTotal = 0;

    //在屏幕上可见的item数量
    private int visibleItemCount;

    //在屏幕可见的Item中的第一个
    private int firstVisibleItem;

    //是否正在上拉数据
    public static boolean loading = true;

    public EndLessOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        currentPage = SpUtils.getInt(recyclerView.getContext(),Constants.CURRENT_PAGE,0);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if(loading){

            if(totalItemCount > previousTotal){
                //说明数据已经加载结束
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        previousTotal = totalItemCount;  //为解决下拉刷新和上拉加载更多冲突（最后一页数据较少）

        //这里需要好好理解
        if (!loading && totalItemCount-visibleItemCount <= firstVisibleItem){
            currentPage ++;
            SpUtils.putInt(recyclerView.getContext(), Constants.CURRENT_PAGE,currentPage);  //加载页码数
                onLoadMore(currentPage);
                loading = true;

        }

    }

    /**
     * 提供一个抽闲方法，在Activity中监听到这个EndLessOnScrollListener
     * 并且实现这个方法
     * */
    public abstract void onLoadMore(int currentPage);
}
