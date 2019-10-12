package com.code_base_update.ui;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.models.ComplaintListModel;
import com.code_base_update.presenters.IComplaintListPresenter;
import com.code_base_update.ui.adapters.NewComplaintAdapter;
import com.code_base_update.view.IComplaintListView;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;

public class ComplaintComplaintListActivity extends BaseRecyclerActivity<IComplaintListView, IComplaintListPresenter,NewComplaintAdapter> implements IComplaintListView {

    private ArrayList<ComplaintBean> list=new ArrayList<>();

    @Override
    public RecyclerView getRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return recyclerView;
    }

    @Override
    public NewComplaintAdapter getAdapter() {
        return new NewComplaintAdapter(this,R.layout.new_complaint_card,list);
    }

    @Override
    public void initViews() {
        setupToolbar("Your Complaint");
        enableNavigation();
        list = new ArrayList<>();
        mPresenter.loadData(this);
    }

    @Override
    public void refreshLayout() {

    }

    @Override
    protected IComplaintListPresenter createPresenter() {
        return new ComplaintListModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.simple_recycler_activity;
    }

    @Override
    public void onListLoaded(ArrayList<ComplaintBean> list) {
        this.list = list;
        adapter.update(list);
    }
}
