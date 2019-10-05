package com.code_base_update.ui.adapters;

import android.content.Context;

import com.code_base_update.Constants;
import com.code_base_update.beans.ComplaintBean;
import com.medeveloper.ayaz.hostelutility.R;
import java.util.Date;
import java.util.List;

public class ScrollingAdapter extends BaseRecyclerAdapter<ComplaintBean> {

    public ScrollingAdapter(Context context, int layoutResId, List<ComplaintBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    void bindData(BaseViewHolder viewHolder, ComplaintBean item, int position) {
        if(!item.getComplaintId().equals(Constants.LOADING_ITEM)) {
            viewHolder.setText(R.id.tv_card_title, item.getComplaintDomainId());
            viewHolder.setText(R.id.tv_card_body, "Problem facing with: " + item.getDetails());
            viewHolder.setText(R.id.tv_card_date, new Date(item.getDate()).toString());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position).getComplaintId().equals(Constants.LOADING_ITEM))
            return BaseRecyclerAdapter.LOADING_VIEW_TYPE;
        return super.getItemViewType(position);
    }

    @Override
    void updateDataOnTouch(int position) {

    }
}
