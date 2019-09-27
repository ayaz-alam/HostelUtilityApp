package com.code_base_update.presenters;

import android.content.Context;

import com.code_base_update.view.IApplicationListView;
import com.code_base_update.view.IComplaintListView;

public interface IApplicationListPresenter extends IBasePresenter<IApplicationListView> {
    void loadData(Context context);
}
