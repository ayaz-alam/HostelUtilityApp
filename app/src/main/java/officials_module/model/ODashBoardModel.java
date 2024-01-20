package officials_module.model;

import com.code_base_update.beans.DashBoardBean;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;

import officials_module.presenter.IODashboardPresenter;
import officials_module.view.IODashboardView;

public class ODashBoardModel implements IODashboardPresenter {

    private IODashboardView mView;

    @Override
    public void loadData() {
        ArrayList<DashBoardBean> list = new ArrayList<>();

        DashBoardBean collegeNotice = new DashBoardBean();
        collegeNotice.setTitle("College Notice");
        collegeNotice.setDrawableId(R.drawable.ic_new_college_notice);
        list.add(collegeNotice);

        DashBoardBean hostelNotice = new DashBoardBean();
        hostelNotice.setTitle("Hostel notice");
        hostelNotice.setDrawableId(R.drawable.ic_new_notice);
        list.add(hostelNotice);

        DashBoardBean complaintActivity = new DashBoardBean();
        complaintActivity.setTitle("Send notice");
        complaintActivity.setDrawableId(R.drawable.new_send_notice);
        list.add(complaintActivity);

        DashBoardBean complaintListActivity = new DashBoardBean();
        complaintListActivity.setTitle("Complaints");
        complaintListActivity.setDrawableId(R.drawable.ic_new_complaint_list);
        list.add(complaintListActivity);

        DashBoardBean registerApplication = new DashBoardBean();
        registerApplication.setTitle("Applications");
        registerApplication.setDrawableId(R.drawable.ic_new_application);
        list.add(registerApplication);

        DashBoardBean applicationList = new DashBoardBean();
        applicationList.setTitle("Staffs and faculty");
        applicationList.setDrawableId(R.drawable.new_staff);
        list.add(applicationList);

        mView.onDataLoaded(list);
    }

    @Override
    public void loadProfileImage() {

        mView.onProfileImageLoaded();
    }

    @Override
    public void attachView(IODashboardView view) {
        mView = view;
    }

    @Override
    public void detachView() {

    }
}
