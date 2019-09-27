package com.code_base_update.ui.adapters;

import android.content.Context;

import com.code_base_update.beans.ComplaintBean;

import java.util.List;

public class ApplicationListAdapter extends BaseRecyclerAdapter<ComplaintBean> {

    public ApplicationListAdapter(Context context, int layoutResId, List<ComplaintBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    void bindData(BaseViewHolder viewHolder, ComplaintBean item, int position) {

    }

    @Override
    void updateDataOnTouch(int position) {

    }
}
