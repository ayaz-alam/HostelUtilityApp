package com.code_base_update.ui;

import androidx.recyclerview.widget.RecyclerView;

import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.models.ApplicationListModel;
import com.code_base_update.presenters.IApplicationListPresenter;
import com.code_base_update.ui.adapters.ApplicationListAdapter;
import com.code_base_update.view.IApplicationListView;
import com.code_base_update.view.IApplicationView;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;

public class ApplicationListActivity extends BaseRecyclerActivity<IApplicationListView, IApplicationListPresenter, ApplicationListAdapter> implements IApplicationView {

    ArrayList<ApplicationBean> list;
    @Override
    public RecyclerView getRecyclerView() {
        return (RecyclerView)getView(R.id.recycler_view);
    }

    @Override
    public ApplicationListAdapter getAdapter() {
        return new ApplicationListAdapter(this, R.layout.card_diet_off,list);
    }

    @Override
    public void initViews() {
        list = new ArrayList<>();
        mPresenter.loadData(this);
        enableNavigation();
    }

    @Override
    protected IApplicationListPresenter createPresenter() {
        return new ApplicationListModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.simple_recycler_activity;
    }

    @Override
    public void clearView() {

    }

    @Override
    public void onInitiated() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(String msg) {

    }
}
