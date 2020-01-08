package officials_module.view;

import com.code_base_update.beans.DashBoardBean;
import com.code_base_update.view.IBaseView;

import java.util.ArrayList;

public interface IODashboardView extends IBaseView {
    void onDataLoaded(ArrayList<DashBoardBean> list);
    void onProfileImageLoaded();
}
