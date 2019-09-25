package com.code_base_update.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.code_base_update.interfaces.OnItemClickListener;

public abstract class BaseRecyclerAdapter<T extends Comparable<? super T>>
        extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private int mLayoutResId;
    private LayoutInflater mLayoutInflater;
    private List<T> mData;
    private OnItemClickListener itemClickListener;
    private int selectedPosition = -1;


    public BaseRecyclerAdapter(Context context, int layoutResId, List<T> data) {
        super();
        mData = data;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        if (layoutResId != 0) {
            mLayoutResId = layoutResId;
        }
    }

    abstract void bindData(BaseViewHolder viewHolder, T item, int
            position);

    abstract void updateDataOnTouch(int position);

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = null;
        switch (viewType) {
            default:
                baseViewHolder = new BaseViewHolder(mContext,
                        mLayoutInflater.inflate(mLayoutResId, parent, false));
                break;
        }
        initClickListener(baseViewHolder);
        return baseViewHolder;
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            default:
                bindData(holder, mData.get(position), position);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private void getViewByResId(int resId, ViewGroup parent) {
        mLayoutInflater.inflate(resId, parent, false);
    }


    public void add(T item) {
        mData.add(item);
        notifyItemInserted(mData.size() - 1);
    }


    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }


    public void addAll(List<T> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private void initClickListener(final BaseViewHolder baseViewHolder) {
        if (itemClickListener != null) {
            baseViewHolder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = baseViewHolder.getAdapterPosition();
                    itemClickListener.onItemClick(baseViewHolder
                            .getAdapterPosition());
                    updateDataOnTouch(selectedPosition);
                }
            });
        }
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}
