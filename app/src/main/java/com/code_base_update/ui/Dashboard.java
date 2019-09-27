package com.code_base_update.ui;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import com.code_base_update.beans.DashBoardBean;
import com.code_base_update.presenters.IDashPresenter;
import com.code_base_update.ui.adapters.DashboardRecyclerAdapter;
import com.code_base_update.view.IDashView;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;

public class Dashboard extends BaseRecyclerActivity<IDashView,IDashPresenter, DashboardRecyclerAdapter> implements IDashView{

    private ArrayList<DashBoardBean> list;

    @Override
    protected IDashPresenter createPresenter() {
        return new DashboardModel();
    }

    @Override
    RecyclerView getRecyclerView() {
        return null;
    }

    @Override
    DashboardRecyclerAdapter getAdapter() {
        return new DashboardRecyclerAdapter(this,R.layout.new_dashboard_cardui,list);
    }

    @Override
    void initViews() {
        list = new ArrayList<>();
        mPresenter.loadData();

    }

    @Override
    protected void initViewsAndEvents() {



    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_activity_dashboard;
    }

    @Override
    public void onDataLoaded(ArrayList<DashBoardBean> list) {
        this.list = list;
        adapter.update(list);
    }

    @Override
    public void openRegisterComplaint() {
        startActivity(new Intent(this,ComplaintActivity.class));
    }

    @Override
    public void openRegisterComplaintList() {
        startActivity(new Intent(this, ComplaintComplaintListActivity.class));
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
