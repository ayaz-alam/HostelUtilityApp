package com.code_base_update.models;

import com.code_base_update.presenters.IDashPresenter;
import com.code_base_update.view.IDashView;

public class DashboardModel implements IDashPresenter {
    private IDashView view;

    @Override
    public void attachView(IDashView view) {
        this.view =view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void loadData() {

    }
}
