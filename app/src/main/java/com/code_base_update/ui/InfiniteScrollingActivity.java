package com.code_base_update.ui;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.models.ScrollerModel;
import com.code_base_update.presenters.IScrollingPresenter;
import com.code_base_update.ui.adapters.ScrollingAdapter;
import com.code_base_update.view.IScrollingView;
import com.medeveloper.ayaz.hostelutility.R;
import java.util.ArrayList;

public class InfiniteScrollingActivity extends BaseRecyclerActivity<IScrollingView<ComplaintBean>, IScrollingPresenter, ScrollingAdapter> implements IScrollingView<ComplaintBean>{

    private int pageSize=10;
    private boolean isLoading  = false;
    private boolean isScrolling = false;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected IScrollingPresenter createPresenter() {
        return new ScrollerModel(pageSize,this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.simple_recycler_activity;
    }

    @Override
    public RecyclerView getRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return recyclerView;
    }

    @Override


    public ScrollingAdapter getAdapter() {
        return new ScrollingAdapter(this,R.layout.new_complaint_card,new ArrayList<ComplaintBean>());
    }

    @Override
    public void initViews() {
        swipeRefreshLayout = findViewById(R.id.refresh_recycler_view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                mPresenter.loadFirstBatch();
            }
        });
        mPresenter.loadFirstBatch();
    }

    private void setupScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState== RecyclerView.SCROLL_STATE_IDLE){
                    isScrolling = false;
                }else if(newState ==RecyclerView.SCROLL_STATE_DRAGGING){
                    isScrolling =true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int lastVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if(!isLoading&&lastVisibleItem<totalItemCount+pageSize){
                    isLoading = true;
                    mPresenter.loadNextBatch();
                }
            }
        });

    }

    @Override
    public void firstPageReceived(ArrayList<ComplaintBean> list) {
        /*if(pageSize<=list.size())
            list.add(ComplaintBean.getLoadingComponent());*/
        adapter.update(list);
        isLoading = false;
        swipeRefreshLayout.setRefreshing(false);
        setupScrollListener();
    }

    @Override
    public void onNextPageReceived(ArrayList<ComplaintBean> list) {
        //adapter.remove(adapter.getItemCount()-1);
      /*  if(pageSize<=list.size())
            list.add(ComplaintBean.getLoadingComponent());*/
        adapter.addAll(list);
        isLoading = false;
    }

    @Override
    public void onEndOfListReached() {
        if(adapter.getItemCount()>0)
            adapter.remove(adapter.getItemCount()-1);
    }

    @Override
    public void onErrorOccurred(String message) {
        Toast.makeText(this,"Error: "+message,Toast.LENGTH_LONG).show();
    }
}
