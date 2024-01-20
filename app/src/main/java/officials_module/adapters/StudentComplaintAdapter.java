package officials_module.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Button;

import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.ui.adapters.BaseRecyclerAdapter;
import com.code_base_update.ui.adapters.BaseViewHolder;
import com.code_base_update.utility.DateUtils;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.List;

public class StudentComplaintAdapter extends BaseRecyclerAdapter<ComplaintBean> {

    public StudentComplaintAdapter(Context context, List<ComplaintBean> data) {
        super(context, R.layout.new_complaint_card, data);
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, ComplaintBean item, int position) {

        viewHolder.setText(R.id.tv_card_title, item.getComplaintDomainId());
        StringBuilder description = new StringBuilder("Problem facing with: " + item.getDetails());

        if(!TextUtils.isEmpty(item.getOptionalDescription())){
            description.append("\n\n");
            description.append(item.getOptionalDescription());
        }

        viewHolder.setText(R.id.tv_card_body,description.toString());
        viewHolder.setText(R.id.tv_card_date, DateUtils.getTime(item.getDate()));
        Button resolveButton = viewHolder.getView(R.id.btn_resolve);

        String name = item.getStudentName()+" : "+item.getRoomNo();

        viewHolder.setText(R.id.tv_guid,name);

        if(item.isResolved()){
            resolveButton.setText("Resolved");
            viewHolder.getView(R.id.btn_resolve).setClickable(false);
            resolveButton.setBackground(null);
            viewHolder.setBackgroundRes(R.id.btn_resolve,R.drawable.success_button_green);
            resolveButton.setTextColor(mContext.getResources().getColor(R.color.success_green));
            viewHolder.setImageResource(R.id.iv_status_symbol,R.drawable.ic_success_icon);
        }
        else{
            viewHolder.getView(R.id.btn_resolve).setClickable(false);
            viewHolder.setBackgroundRes(R.id.btn_resolve,R.drawable.round_white);
            resolveButton.setText("Not Resolved");
            resolveButton.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            viewHolder.setImageResource(R.id.iv_status_symbol,R.drawable.ic_new_waiting);
        }

    }

    @Override
    protected void updateDataOnTouch(int position) {
        //Bequest Code Smell
    }
}
