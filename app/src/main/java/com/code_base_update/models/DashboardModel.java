package com.code_base_update.models;

import com.code_base_update.UserManager;
import com.code_base_update.beans.DashBoardBean;
import com.code_base_update.presenters.IDashPresenter;
import com.code_base_update.view.IDashView;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;

public class DashboardModel implements IDashPresenter {
    private IDashView view;

    @Override
    public void attachView(IDashView view) {
        this.view =view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void loadData() {
        ArrayList<DashBoardBean> list = new ArrayList<>();

        DashBoardBean collegeNotice = new DashBoardBean();
        collegeNotice.setTitle("College Notice");
        collegeNotice.setDrawableId(R.drawable.ic_general_notice);
        list.add(collegeNotice);

        DashBoardBean hostelNotice = new DashBoardBean();
        hostelNotice.setTitle("Hostel Notice");
        hostelNotice.setDrawableId(R.drawable.ic_notice);
        list.add(hostelNotice);

        DashBoardBean complaintActivity = new DashBoardBean();
        complaintActivity.setTitle("Register Complaint");
        complaintActivity.setDrawableId(R.drawable.ic_complaint);
        list.add(complaintActivity);

        DashBoardBean complaintListActivity = new DashBoardBean();
        complaintListActivity.setTitle("Your Complaints");
        complaintListActivity.setDrawableId(R.drawable.ic_complaint_list);
        list.add(complaintListActivity);

        DashBoardBean registerApplication = new DashBoardBean();
        registerApplication.setTitle("Register Application");
        registerApplication.setDrawableId(R.drawable.ic_diet_off);
        list.add(registerApplication);

        DashBoardBean applicationList = new DashBoardBean();
        applicationList.setTitle("Your applications");
        applicationList.setDrawableId(R.drawable.ic_complaint_list);
        list.add(applicationList);


        view.onDataLoaded(list);
    }

    @Override
    public void loadUserName() {
        view.userNameLoaded(new UserManager().getName());
    }

    @Override
    public void loadUserImageUrl() {
        view.onDisplayImageLoaded(new UserManager().getImageUrl());
    }


}
