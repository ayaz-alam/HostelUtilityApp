package com.code_base_update.models;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.code_base_update.DatabaseManager;
import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.interfaces.DataCallback;
import com.code_base_update.interfaces.SuccessCallback;
import com.code_base_update.presenters.IApplicationListPresenter;
import com.code_base_update.view.IApplicationListView;

import java.util.ArrayList;

public class ApplicationListModel implements IApplicationListPresenter {

    private IApplicationListView iApplicationView;

    @Override
    public void attachView(IApplicationListView view) {
        this.iApplicationView = view;
    }

    @Override
    public void detachView() {
        iApplicationView = null;
    }

    @Override
    public void loadData(final Context context) {
        new AsyncTaskLoader(context) {

            @Nullable
            @Override
            public Void loadInBackground() {
                new DatabaseManager(context).loadAllApplication(new DataCallback<ArrayList<ApplicationBean>>() {
                    @Override
                    public void onSuccess(ArrayList<ApplicationBean> applicationBeans) {
                        iApplicationView.onListLoaded(applicationBeans);
                    }

                    @Override
                    public void onFailure(String msg) {
                        iApplicationView.onFailure(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        iApplicationView.onFailure(msg);
                    }
                });
                return null;
            }

        }.loadInBackground();

    }

    @Override
    public void sendReminder(Context context,ApplicationBean item, SuccessCallback callback) {
        callback.onInitiated();
        new DatabaseManager(context).sendApplicationReminder(item,callback);
    }

    @Override
    public void withdrawApplication(Context context,ApplicationBean item, SuccessCallback callback) {
        callback.onInitiated();
        new DatabaseManager(context).withdrawApplication(item,callback);

    }

}
