package com.code_base_update.ui.adapters;

import android.app.Application;
import android.content.Context;

import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.beans.ComplaintBean;

import java.util.List;

public class ApplicationListAdapter extends BaseRecyclerAdapter<ApplicationBean> {


    public ApplicationListAdapter(Context context, int layoutResId, List<ApplicationBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    void bindData(BaseViewHolder viewHolder, ApplicationBean item, int position) {

    }

    @Override
    void updateDataOnTouch(int position) {

    }
}
