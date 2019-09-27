package com.code_base_update.models;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.code_base_update.DatabaseManager;
import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.presenters.IApplicationListPresenter;
import com.code_base_update.presenters.IComplaintListPresenter;
import com.code_base_update.view.IApplicationListView;
import com.code_base_update.view.IApplicationView;
import com.code_base_update.view.IComplaintListView;

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
    public void loadData(Context context) {
        new AsyncTaskLoader<ArrayList<ApplicationBean>>(context){

            @Nullable
            @Override
            public ArrayList<ApplicationBean> loadInBackground() {
              ArrayList<ApplicationBean> list  = new DatabaseManager().loadAllApplication();
              iApplicationView.onListLoaded(list);
              return list;
            }

        };

    }

}
