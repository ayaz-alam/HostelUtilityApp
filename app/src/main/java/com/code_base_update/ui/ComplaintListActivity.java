package com.code_base_update.ui;

import androidx.recyclerview.widget.RecyclerView;
import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.models.ComplaintListModel;
import com.code_base_update.presenters.IComplaintListPresenter;
import com.code_base_update.ui.adapters.NewComplaintAdapter;
import com.code_base_update.view.IComplaintListView;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;

public class ComplaintListActivity extends BaseRecyclerActivity<IComplaintListView, IComplaintListPresenter,NewComplaintAdapter> implements IComplaintListView{

    private ArrayList<ComplaintBean> list;

    @Override
    RecyclerView getRecyclerView() {
        return (RecyclerView)getView(R.id.recycler_view);
    }

    @Override
    NewComplaintAdapter getAdapter() {
        return new NewComplaintAdapter(this,R.layout.card_complaint_student,list);
    }

    @Override
    void initViews() {
        list = new ArrayList<>();
        mPresenter.loadData(this);
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
