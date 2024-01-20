package com.code_base_update.ui;

import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.ui.adapters.BaseRecyclerAdapter;
import com.code_base_update.view.IBaseView;
import com.medeveloper.ayaz.hostelutility.R;


public abstract class BaseRecyclerActivity<V extends IBaseView, P extends IBasePresenter<V>, A extends BaseRecyclerAdapter> extends BaseActivity<V, P> {

    public A adapter;
    public RecyclerView recyclerView;
    public ProgressBar progressBar;
    public SwipeRefreshLayout swipeRefreshLayout;

    public abstract RecyclerView getRecyclerView();

    public abstract A getAdapter();

    public abstract void initViews();

    public abstract void refreshLayout();

    @Override
    protected void initViewsAndEvents() {
        adapter = getAdapter();
        recyclerView = getRecyclerView();
        recyclerView.setAdapter(adapter);
        progressBar = (ProgressBar) getView(R.id.pg_loading);
        swipeRefreshLayout = (SwipeRefreshLayout) getView(R.id.refresh_recycler_view);
        showNoData(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout();
            }
        });
        initViews();
    }

    public void showProgressBar(boolean show){
        setVisible(R.id.pg_loading,show);
    }

    public void showNoData(boolean show) {
        setVisible(R.id.no_data_view,show);
    }

    protected void onDataLoaded(int size) {
        showProgressBar(false);
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        if(size==0)
            showNoData(true);
        else showNoData(false);
    }
}
