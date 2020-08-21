package com.zl.shyhttp.commonAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zhangli on 2019/4/23.
 */

public abstract class RecyclerCommonAdapter<DATA> extends RecyclerView.Adapter<ViewHolder> {

    private List<DATA> mDatas;
    protected Context mContext;
    private LayoutInflater mInflater;
    private int mLayoutId;
    private ItemOnClick mItemOnClick;
    private MultipleTypeSupport mTypeSupport;

    public RecyclerCommonAdapter(List<DATA> mDatas, Context mContext, int mLayoutId) {
        this.mDatas = mDatas;
        this.mContext = mContext.getApplicationContext();
        this.mInflater = LayoutInflater.from(mContext);
        this.mLayoutId = mLayoutId;
    }

    public RecyclerCommonAdapter(List<DATA> mDatas, Context mContext, MultipleTypeSupport multipleTypeSupport) {
        this(mDatas, mContext, -1);
        this.mTypeSupport = multipleTypeSupport;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mTypeSupport != null) {
            mLayoutId = viewType;
        }
        View itemView = mInflater.inflate(mLayoutId, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        if (mTypeSupport != null) {
            return mTypeSupport.getLayoutId(mDatas.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        covert(holder, mDatas.get(position), position);
        if (mItemOnClick != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemOnClick.onItemClick(position, mDatas.get(position));
                }
            });
        }
    }

    protected abstract void covert(ViewHolder holder, DATA data, int position);

    public void setmItemOnClick(ItemOnClick mItemOnClick) {
        this.mItemOnClick = mItemOnClick;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

}
