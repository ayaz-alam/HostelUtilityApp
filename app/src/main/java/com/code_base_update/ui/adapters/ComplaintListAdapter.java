package com.code_base_update.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Button;

import com.code_base_update.Constants;
import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.interfaces.OnItemClickListener;
import com.code_base_update.utility.DateUtils;
import com.medeveloper.ayaz.hostelutility.R;
import java.util.List;

public class ComplaintListAdapter extends BaseRecyclerAdapter<ComplaintBean> {

    private OnItemClickListener callback;

    public ComplaintListAdapter(Context context, int layoutResId, List<ComplaintBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    void bindData(BaseViewHolder viewHolder, ComplaintBean item, int position) {
        if(!item.getComplaintId().equals(Constants.LOADING_ITEM)) {
            viewHolder.setText(R.id.tv_card_title, item.getComplaintDomainId());
            StringBuilder description = new StringBuilder("Problem facing with: " + item.getDetails());
            if(!TextUtils.isEmpty(item.getOptionalDescription()))
            {
                description.append("\n\n");
                description.append(item.getOptionalDescription());
            }
            viewHolder.setText(R.id.tv_card_body,description.toString());
            viewHolder.setText(R.id.tv_card_date, DateUtils.getTime(item.getTimeStamp()));
            if(callback!=null) viewHolder.setItemClickListener(R.id.btn_resolve,callback);
            Button resolveButton = viewHolder.getView(R.id.btn_resolve);
            if(item.isResolved()){
                resolveButton.setText("Resolved");
                viewHolder.setBackgroundRes(R.id.btn_resolve,R.drawable.success_button_green);
                viewHolder.setImageResource(R.id.iv_status_symbol,R.drawable.ic_success_icon);
            }else{
                viewHolder.setBackgroundRes(R.id.btn_resolve,R.drawable.new_submit_btn);
                resolveButton.setText("Mark Resolved");
                viewHolder.setImageResource(R.id.iv_status_symbol,R.drawable.ic_new_waiting);
            }
        }
    }

    public void setOnResolveLister(OnItemClickListener listener){
        this.callback = listener;
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
