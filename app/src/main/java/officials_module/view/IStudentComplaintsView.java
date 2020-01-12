package officials_module.view;

import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.view.IBaseView;

import java.util.ArrayList;

public interface IStudentComplaintsView extends IBaseView {
    void onComplaintListLoaded(ArrayList<ComplaintBean> list);
    void onError(String msg);
}
