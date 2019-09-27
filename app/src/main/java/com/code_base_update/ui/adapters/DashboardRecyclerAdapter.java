package com.code_base_update.ui.adapters;

import android.content.Context;

import com.code_base_update.beans.DashBoardBean;

import java.util.List;

public class DashboardRecyclerAdapter extends BaseRecyclerAdapter<DashBoardBean> {
    public DashboardRecyclerAdapter(Context context, int layoutResId, List<DashBoardBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    void bindData(BaseViewHolder viewHolder, DashBoardBean item, int position) {

    }

    @Override
    void updateDataOnTouch(int position) {

    }
}
