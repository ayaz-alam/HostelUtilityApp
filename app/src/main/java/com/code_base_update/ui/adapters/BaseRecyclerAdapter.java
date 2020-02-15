package com.code_base_update.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.code_base_update.beans.BaseBean;
import com.code_base_update.interfaces.OnItemClickListener;
import com.medeveloper.ayaz.hostelutility.R;

public abstract class BaseRecyclerAdapter<T extends BaseBean>
        extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int LOADING_VIEW_TYPE = 124;
    public Context mContext;
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

    public abstract void bindData(BaseViewHolder viewHolder, T item, int
            position);

    protected abstract void updateDataOnTouch(int position);

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = null;

        int mLoadingResId = R.layout.loading_card;
        switch (viewType) {
            case LOADING_VIEW_TYPE:
                baseViewHolder = new BaseViewHolder(mContext,
                        mLayoutInflater.inflate(mLoadingResId, parent, false));

                break;
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

    public void add(T item) {
        mData.add(item);
        notifyItemInserted(mData.size() - 1);
    }

    public void update(ArrayList<T> items) {
        mData = items;
        notifyDataSetChanged();
    }


    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }


    public void addAll(List<T> data) {
        int start = mData.size() - 1;
        mData.addAll(data);
        notifyItemRangeInserted(start, mData.size() - 1);
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

    public T getItem(int position) {
        return mData.get(position);
    }

    public void clear(){
        mData = new ArrayList<T>();
        notifyDataSetChanged();
    }
}
