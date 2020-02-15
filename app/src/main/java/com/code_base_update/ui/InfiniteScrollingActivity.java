package com.code_base_update.ui;

import android.content.DialogInterface;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.interfaces.OnItemClickListener;
import com.code_base_update.interfaces.SuccessCallback;
import com.code_base_update.models.ScrollerModel;
import com.code_base_update.presenters.IScrollingPresenter;
import com.code_base_update.ui.adapters.ComplaintListAdapter;
import com.code_base_update.view.IScrollingView;
import com.medeveloper.ayaz.hostelutility.R;

import java.util.ArrayList;

public class InfiniteScrollingActivity extends BaseRecyclerActivity<IScrollingView<ComplaintBean>, IScrollingPresenter, ComplaintListAdapter> implements IScrollingView<ComplaintBean> {

    private int pageSize = 10;
    private boolean isLoading = false;
    private boolean isScrolling = false;

    @Override
    protected IScrollingPresenter createPresenter() {
        return new ScrollerModel(pageSize, this);
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


    public ComplaintListAdapter getAdapter() {
        return new ComplaintListAdapter(this, R.layout.new_complaint_card, new ArrayList<ComplaintBean>());
    }

    @Override
    public void initViews() {
        setupToolbar("Your complaints");
        enableNavigation();
        adapter.setOnResolveLister(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showResolveDialog(position);
            }
        });
        mPresenter.loadFirstBatch();
    }

    @Override
    public void refreshLayout() {
        adapter.clear();
        mPresenter.loadFirstBatch();
    }

    private void showResolveDialog(final int position) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Mark complaint as Resolved ?");
        dialog.setMessage("Do you really want to mark this complaint as resolved");
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Mark resolved", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.markResolved(adapter.getItem(position), new SuccessCallback() {
                    @Override
                    public void onInitiated() {
                        toastMsg("Resolving...");
                    }

                    @Override
                    public void onSuccess() {
                        toastMsg("Successfully Resolved");
                        adapter.getItem(position).setResolved(true);
                        adapter.notifyItemChanged(position);
                    }

                    public void onFailure(String msg) {
                        toastMsg("Failed: " + msg);
                    }
                });
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();


    }

    private void setupScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isScrolling = false;
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if (!isLoading && lastVisibleItem < totalItemCount + pageSize) {
                    isLoading = true;
                   // mPresenter.loadNextBatch();
                }
            }
        });

    }

    @Override
    public void firstPageReceived(ArrayList<ComplaintBean> list) {
            showNoData(false);
        /*if(pageSize<=list.size())
            list.add(ComplaintBean.getLoadingComponent());*/
            showProgressBar(false);
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
        toastMsg("End of list reached");
    }

    @Override
    public void onErrorOccurred(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void noDataFound() {
        swipeRefreshLayout.setRefreshing(false);
        showNoData(true);
        showProgressBar(false);
    }
}
