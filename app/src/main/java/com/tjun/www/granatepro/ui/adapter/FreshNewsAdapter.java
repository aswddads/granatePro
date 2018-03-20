package com.tjun.www.granatepro.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.FreshNewsBean;
import com.tjun.www.granatepro.utils.GildUtils;

import java.util.List;

/**
 * Created by tanjun on 2018/3/15.
 */
public class FreshNewsAdapter extends BaseQuickAdapter<FreshNewsBean.PostsBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {
    private Context mContext;

    public FreshNewsAdapter(Context context, @Nullable List<FreshNewsBean.PostsBean> data) {
        super(R.layout.item_freshnews, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, FreshNewsBean.PostsBean postsBean) {
        viewHolder.setText(R.id.tv_title, postsBean.getTitle());
        viewHolder.setText(R.id.tv_info, postsBean.getAuthor().getName());
        viewHolder.setText(R.id.tv_commnetsize, postsBean.getComment_count() + "评论");
        GildUtils.LoadImage(mContext, postsBean.getCustom_fields().getThumb_c().get(0), (ImageView) viewHolder.getView(R.id.iv_logo));
       // setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//        View view1 = baseQuickAdapter.getViewByPosition(i,R.id.iv_logo);
//        ReadActivity.launch(mContext, (FreshNewsBean.PostsBean) baseQuickAdapter.getItem(i));
    }
}
