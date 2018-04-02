package com.tjun.www.granatepro.ui.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tjun.www.granatepro.R;
import com.tjun.www.granatepro.bean.MySelect;
import com.tjun.www.granatepro.utils.GildUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by tanjun on 2018/4/2.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public List<MySelect> mData;
    private Context mContext;

    /**
     * 事件回调监听
     */
    private MyAdapter.OnItemClickListener onItemClickListener;

    public MyAdapter(List<MySelect> mData, Context context) {
        this.mData = mData;
        Collections.reverse(mData);
        this.mContext = context;
    }

    public void updateData(List<MySelect> data){
        this.mData = data;
        Collections.reverse(mData);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect_news,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        GildUtils.LoadImage(mContext,mData.get(position).getImg(),holder.mIvImg);
        holder.mTvTitle.setText(mData.get(position).getTitle());
        holder.mTvSource.setText(mData.get(position).getSource());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
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

    /**
     * 移除item
     * @param position
     */
    public void deleteItem(int position) {
        if(mData == null || mData.isEmpty()) {
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

}
