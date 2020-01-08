package officials_module.adapters;

import android.content.Context;

import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.ui.adapters.BaseRecyclerAdapter;
import com.code_base_update.ui.adapters.BaseViewHolder;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.List;

public class StudentComplaintAdapter extends BaseRecyclerAdapter<ComplaintBean> {

    public StudentComplaintAdapter(Context context, List<ComplaintBean> data) {
        super(context, R.layout.new_complaint_card, data);
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, ComplaintBean item, int position) {

    }

    @Override
    protected void updateDataOnTouch(int position) {
        //Bequest Code Smell
    }
}
