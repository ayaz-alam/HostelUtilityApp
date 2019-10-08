package com.code_base_update.models;

import android.content.Context;

import com.code_base_update.DatabaseManager;
import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.presenters.IApplicationPresenter;
import com.code_base_update.view.IApplicationView;

public class AddApplicationModel implements IApplicationPresenter{

    private IApplicationView iApplicationView;

    @Override
    public void attachView(IApplicationView view) {
        this.iApplicationView =view;
    }

    @Override
    public void detachView() {
        this.iApplicationView = null;
    }

    @Override
    public void saveApplication(ApplicationBean application, Context context) {
        new DatabaseManager(context).saveApplication(iApplicationView,application);
    }
}
