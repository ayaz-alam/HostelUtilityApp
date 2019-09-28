package com.code_base_update.ui.adapters;

import android.content.Context;

import com.code_base_update.beans.HostelNoticeBean;

import java.util.List;

public class HostelNoticeAdapter extends BaseRecyclerAdapter<HostelNoticeBean>{

    public HostelNoticeAdapter(Context context, int layoutResId, List<HostelNoticeBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    void bindData(BaseViewHolder viewHolder, HostelNoticeBean item, int position) {

    }

    @Override
    void updateDataOnTouch(int position) {

    }
}
