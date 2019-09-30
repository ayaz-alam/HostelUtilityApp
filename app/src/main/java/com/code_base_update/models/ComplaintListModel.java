package com.code_base_update.models;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.code_base_update.DatabaseManager;
import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.presenters.IComplaintListPresenter;
import com.code_base_update.view.IComplaintListView;

import java.util.ArrayList;

public class ComplaintListModel implements IComplaintListPresenter {

    private IComplaintListView iComplaintListView;


    @Override
    public void attachView(IComplaintListView view) {
        this.iComplaintListView = view;
    }

    @Override
    public void detachView() {
        iComplaintListView = null;
    }

    @Override
    public void loadData(final Context context) {
        AsyncTaskLoader<ArrayList<ComplaintBean>> loader = new AsyncTaskLoader<ArrayList<ComplaintBean>>(context){
            @Nullable
            @Override
            public ArrayList<ComplaintBean> loadInBackground() {
              ArrayList<ComplaintBean> list  = new DatabaseManager(context).loadAllComplaint();
              iComplaintListView.onListLoaded(list);
              return list;
            }

        };
        loader.loadInBackground();

    }
}
