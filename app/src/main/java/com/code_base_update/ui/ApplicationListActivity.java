package com.code_base_update.ui;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.interfaces.OnItemClickListener;
import com.code_base_update.interfaces.SuccessCallback;
import com.code_base_update.models.ApplicationListModel;
import com.code_base_update.presenters.IApplicationListPresenter;
import com.code_base_update.ui.adapters.ApplicationListAdapter;
import com.code_base_update.view.IApplicationListView;
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
        return new ApplicationListAdapter(this, new ArrayList<ApplicationBean>());
    }

    @Override
    public void initViews() {
        setupToolbar("Your Application");
        enableNavigation();
        list = new ArrayList<>();
        mPresenter.loadData(this);
        adapter.setReminderCallback(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showReminderDialog(position);
            }
        });
        adapter.setWithdrawCallback(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showWithDrawDialog(position);
            }
        });
    }

    @Override
    public void refreshLayout() {
        mPresenter.loadData(mContext);
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
        showProgressBar(false);
        swipeRefreshLayout.setRefreshing(false);
        adapter.clear();
        adapter.update(complaintBean);
    }

    @Override
    public void onFailure(String msg) {
        toastMsg(msg);
        showProgressBar(false);
        showNoData(true);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showReminderDialog(final int position) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Send reminder?");
        dialog.setMessage("Do you really want to send reminders to warden");
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Send reminder", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.sendReminder(mContext, adapter.getItem(position), new SuccessCallback() {
                    @Override
                    public void onInitiated() {
                        toastMsg("Sending...");
                    }

                    @Override
                    public void onSuccess() {
                        toastMsg("Successfully Sent");
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

    private void showWithDrawDialog(final int position) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Withdraw application?");
        dialog.setMessage("Do you really want to withdraw application");
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Withdraw", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.withdrawApplication(mContext, adapter.getItem(position), new SuccessCallback() {
                    @Override
                    public void onInitiated() {
                        toastMsg("Please wait...");
                    }

                    @Override
                    public void onSuccess() {
                        toastMsg("Successful");
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
}
