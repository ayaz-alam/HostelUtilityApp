package com.code_base_update.ui.adapters;

import android.content.Context;
import android.widget.TextView;

import com.code_base_update.beans.HostelNoticeBean;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.List;

public class HostelNoticeAdapter extends BaseRecyclerAdapter<HostelNoticeBean>{

    public HostelNoticeAdapter(Context context, int layoutResId, List<HostelNoticeBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    void bindData(BaseViewHolder viewHolder, HostelNoticeBean item, int position) {
        viewHolder.setText(R.id.tv_notice_title,item.getNoticeId());
        int noOfLines = ((TextView)viewHolder.getView(R.id.tv_notice_body)).getLineCount();
        if(noOfLines>2) {
            ((TextView)viewHolder.getView(R.id.tv_notice_body)).setMaxLines(2);
            viewHolder.setVisible(R.id.read_more,true);
        }
        else viewHolder.setVisible(R.id.read_more,false);
    }

    @Override
    void updateDataOnTouch(int position) {

    }
}
