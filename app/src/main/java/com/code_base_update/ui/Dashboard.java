package com.code_base_update.ui;

import android.content.Intent;

import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.view.IDashView;
import com.medeveloper.ayaz.hostelutility.R;

public class Dashboard extends BaseActivity<IDashView,IBasePresenter<IDashView>> implements IDashView{

    @Override
    protected IBasePresenter<IDashView> createPresenter() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {


    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_activity_dashboard;
    }

    @Override
    public void openRegisterComplaint() {
        startActivity(new Intent(this,ComplaintActivity.class));
    }

    @Override
    public void openRegisterComplaintList() {
        startActivity(new Intent(this,ComplaintListActivity.class));
    }

    @Override
    public void openRegisterApplication() {
        startActivity(new Intent(this,RegisterApplicationActivity.class));
    }

    @Override
    public void openRegisterApplicationList() {
        startActivity(new Intent(this,ApplicationListActivity.class));
    }
}
