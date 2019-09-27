package com.code_base_update.view;

import com.code_base_update.beans.DashBoardBean;

import java.util.ArrayList;

public interface IDashView extends IBaseView {
    void onDataLoaded(ArrayList<DashBoardBean> list);
    void openRegisterComplaint();
    void openRegisterComplaintList();
    void openRegisterApplication();
    void openRegisterApplicationList();
}
