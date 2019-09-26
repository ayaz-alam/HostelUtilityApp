package com.code_base_update.presenters;

import android.content.Context;

import com.code_base_update.view.IComplaintListView;

public interface IComplaintListPresenter extends IBasePresenter<IComplaintListView> {
    void loadData(Context context);
}
