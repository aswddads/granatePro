package com.tjun.www.granatepro.ui.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.MySelect;
import com.tjun.www.granatepro.utils.GildUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tanjun on 2018/4/2.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;//底部

    public List<MySelect> mData;
    private Context mContext;

    FooterViewHolder mFooterViewHolder;

    @BindView(R.id.loading_view)
    LinearLayout mLoadingView;

    /**
     * 事件回调监听
     */
    private MyAdapter.OnItemClickListener onItemClickListener;

    public MyAdapter(List<MySelect> mData, Context context) {
        //Collections.reverse(mData);
        this.mData = mData;
        this.mContext = context;
    }

    public void addData(List<MySelect> data) {
        //Collections.reverse(data);
        this.mData.addAll(data);
        notifyItemInserted(getItemCount()+1);
    }

    public void updateData(List<MySelect> data) {
        //Collections.reverse(data);
//        if (mData != null) {
//            mData = null;
//        }
        mData.clear();
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect_news, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        } else if (viewType == TYPE_FOOTER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collect_news_bottm,parent,false);
            view.setClickable(false);
            return mFooterViewHolder = new FooterViewHolder(view);
        }
        return null;
    }
    

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).mEndViewStub.setVisibility(View.VISIBLE);
            ((FooterViewHolder) holder).mLoadingViewStub.setVisibility(View.GONE);
            ((FooterViewHolder) holder).mNetErrViewStub.setVisibility(View.GONE);
        } else if (holder instanceof ViewHolder) {
            GildUtils.LoadImage(mContext, mData.get(position).getImg(), ((ViewHolder) holder).mIvImg);
            ((ViewHolder) holder).mTvTitle.setText(mData.get(position).getTitle());
            ((ViewHolder) holder).mTvSource.setText(mData.get(position).getSource());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (onItemClickListener != null) {
                        int pos = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, pos);
                    }
                }
            });
        }

    }

    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        return mData.size()+1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mIvImg;//新闻标图
        TextView mTvTitle;//新闻标题
        TextView mTvSource;//新闻出处

        public ViewHolder(View itemView) {
            super(itemView);
            mIvImg = (ImageView) itemView.findViewById(R.id.iv_img);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvSource = (TextView) itemView.findViewById(R.id.tv_source);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.loading_viewstub)
        LinearLayout mLoadingViewStub;

        @BindView(R.id.end_viewstub)
        LinearLayout mEndViewStub;

        @BindView(R.id.network_error_viewstub)
        LinearLayout mNetErrViewStub;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * 移除item
     *
     * @param position
     */
    public void deleteItem(int position) {
        if (mData == null || mData.isEmpty()) {
            return;
        }
        mData.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void hideLoading(){
        mFooterViewHolder.mLoadingViewStub.setVisibility(View.GONE);
    }

    public void showNoMore(){
        mFooterViewHolder.mEndViewStub.setVisibility(View.VISIBLE);
    }

    public void hideNoMore(){
        mFooterViewHolder.mEndViewStub.setVisibility(View.GONE);

    }

    public void showLoading(){
        mFooterViewHolder.mLoadingViewStub.setVisibility(View.VISIBLE);
    }

    public int getMorePageVisable(){
        return mLoadingView.getVisibility();
    }

}
