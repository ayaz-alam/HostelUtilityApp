package com.code_base_update.ui;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.models.ApplicationListModel;
import com.code_base_update.presenters.IApplicationListPresenter;
import com.code_base_update.ui.adapters.ApplicationListAdapter;
import com.code_base_update.view.IApplicationListView;
import com.code_base_update.view.IApplicationView;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;

public class ApplicationListActivity extends BaseRecyclerActivity<IApplicationListView, IApplicationListPresenter, ApplicationListAdapter> implements IApplicationListView {

    private ArrayList<ApplicationBean> list;
    @Override
    public RecyclerView getRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return recyclerView;
    }

    @Override
    public ApplicationListAdapter getAdapter() {
        return new ApplicationListAdapter(this, R.layout.new_card_application,list);
    }

    @Override
    public void initViews() {
        //kamal's code
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().hide();
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
    public void onListLoaded(ArrayList<ApplicationBean> complaintBean) {
        adapter.update(complaintBean);
    }
}
