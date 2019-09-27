package com.code_base_update.ui;

import androidx.recyclerview.widget.RecyclerView;

import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.ui.adapters.ApplicationListAdapter;
import com.code_base_update.view.IBaseView;

public class ApplicationListActivity extends BaseRecyclerActivity<IBaseView, IBasePresenter<IBaseView>, ApplicationListAdapter> {

    @Override
    RecyclerView getRecyclerView() {
        return null;
    }

    @Override
    ApplicationListAdapter getAdapter() {
        return null;
    }

    @Override
    void initViews() {

    }

    @Override
    protected IBasePresenter<IBaseView> createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
