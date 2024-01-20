package com.code_base_update.ui.adapters;

import android.content.Context;

import com.code_base_update.beans.DashBoardBean;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.List;

public class DashboardRecyclerAdapter extends BaseRecyclerAdapter<DashBoardBean> {
    public DashboardRecyclerAdapter(Context context, int layoutResId, List<DashBoardBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, DashBoardBean item, int position) {
        viewHolder.setText(R.id.tv_dashboard_card,item.getTitle());
        viewHolder.setImageResource(R.id.iv_dashboard_icon,item.getDrawableId());

    }

    @Override
    public void updateDataOnTouch(int position) {

    }
}
