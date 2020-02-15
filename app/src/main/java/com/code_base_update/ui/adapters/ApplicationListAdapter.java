package com.code_base_update.ui.adapters;

import android.content.Context;

import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.interfaces.OnItemClickListener;
import com.code_base_update.utility.DateUtils;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.List;

public class ApplicationListAdapter extends BaseRecyclerAdapter<ApplicationBean> {


    private OnItemClickListener reminderCallback;
    private OnItemClickListener withdrawCallback;

    public ApplicationListAdapter(Context context, List<ApplicationBean> data) {
        super(context, R.layout.new_card_application, data);
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, ApplicationBean item, int position) {
        viewHolder.setText(R.id.tv_head, item.getApplicationDomain());
        viewHolder.setText(R.id.tv_subject, item.getSubject());
        viewHolder.setText(R.id.tv_desription, item.getDescription());
        viewHolder.setText(R.id.tv_date, DateUtils.getTime(item.getDate()));

        switch (item.getStatus()) {
            case ApplicationBean.STATUS_ACCEPTED:
                viewHolder.setText(R.id.tv_status, "Accepted on " + DateUtils.getTime(item.getStatusTime()));
                viewHolder.setTextColorRes(R.id.tv_status, R.color.success_green);
                viewHolder.setVisible(R.id.btn_remind, false);
                viewHolder.setVisible(R.id.btn_withdraw, false);
                break;
            case ApplicationBean.STATUS_REJECTED:
                viewHolder.setText(R.id.tv_status, "Rejected on " + DateUtils.getTime(item.getStatusTime()) + "\n Reason: " + item.getStatusDescription());
                viewHolder.setTextColorRes(R.id.tv_status, R.color.warning_red);
                viewHolder.setVisible(R.id.btn_remind, false);
                viewHolder.setVisible(R.id.btn_withdraw, false);
                break;
            case ApplicationBean.STATUS_SEEN:
                viewHolder.setText(R.id.tv_status, "Seen");
                viewHolder.setTextColorRes(R.id.tv_status, R.color.yellow);
                viewHolder.setVisible(R.id.btn_remind, true);
                viewHolder.setVisible(R.id.btn_withdraw, true);
                break;
            case ApplicationBean.STATUS_WITHDRAWN:
                viewHolder.setText(R.id.tv_status, "Withdrawn on "+DateUtils.getTime(item.getStatusTime()));
                viewHolder.setTextColorRes(R.id.tv_status, R.color.warning_red);
                viewHolder.setVisible(R.id.btn_remind, false);
                viewHolder.setVisible(R.id.btn_withdraw, false);
                break;
            default:
                viewHolder.setText(R.id.tv_status, "Waiting");
                viewHolder.setTextColorRes(R.id.tv_status, R.color.yellow);
                viewHolder.setVisible(R.id.btn_remind, true);
                viewHolder.setVisible(R.id.btn_withdraw, true);
        }

        viewHolder.setItemClickListener(R.id.btn_remind, reminderCallback);
        viewHolder.setItemClickListener(R.id.btn_withdraw, withdrawCallback);

    }

    public void setReminderCallback(OnItemClickListener reminderCallback) {
        this.reminderCallback = reminderCallback;
    }

    public void setWithdrawCallback(OnItemClickListener withdrawCallback) {
        this.withdrawCallback = withdrawCallback;
    }

    @Override
    public void updateDataOnTouch(int position) {

    }
}
