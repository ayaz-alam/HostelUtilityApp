package com.code_base_update.ui;

import androidx.recyclerview.widget.RecyclerView;

import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.models.ApplicationListModel;
import com.code_base_update.presenters.IApplicationListPresenter;
import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.ui.adapters.ApplicationListAdapter;
import com.code_base_update.view.IApplicationListView;
import com.code_base_update.view.IBaseView;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;

public class ApplicationListActivity extends BaseRecyclerActivity<IApplicationListView, IApplicationListPresenter, ApplicationListAdapter> {

    ArrayList<ApplicationBean> list;
    @Override
    RecyclerView getRecyclerView() {
        return (RecyclerView)getView(R.id.recycler_view);
    }

    @Override
    ApplicationListAdapter getAdapter() {
        return new ApplicationListAdapter(this, R.layout.card_diet_off,list);
    }

    @Override
    void initViews() {
        list = new ArrayList<>();
        mPresenter.loadData(this);
    }

    @Override
    protected IApplicationListPresenter createPresenter() {
        return new ApplicationListModel();
    }

    @Override
    protected int getLayoutId() {
        //TODO give real id
        return R.layout.simple_recycler_activity;
    }
}
