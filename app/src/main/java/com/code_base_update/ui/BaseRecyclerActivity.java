package com.code_base_update.ui;

import androidx.recyclerview.widget.RecyclerView;

import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.ui.adapters.BaseRecyclerAdapter;
import com.code_base_update.view.IBaseView;

import java.util.ArrayList;


public abstract class BaseRecyclerActivity <V extends IBaseView,P extends IBasePresenter<V>,A extends BaseRecyclerAdapter> extends BaseActivity<V,P>{

    public A adapter;
    public RecyclerView recyclerView;

    abstract RecyclerView getRecyclerView();

    abstract A getAdapter();

    abstract void initViews();

    @Override
    protected void initViewsAndEvents() {
        adapter = getAdapter();
        recyclerView = getRecyclerView();
        recyclerView.setAdapter(adapter);
        initViews();
    }
}
