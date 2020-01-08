package com.code_base_update.presenters;

import android.content.Context;

import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.interfaces.SuccessCallback;
import com.code_base_update.view.IApplicationListView;
import com.code_base_update.view.IComplaintListView;

public interface IApplicationListPresenter extends IBasePresenter<IApplicationListView> {
    void loadData(Context context);
    void sendReminder(Context context,ApplicationBean item, SuccessCallback callback);
    void withdrawApplication(Context context,ApplicationBean item, SuccessCallback successful);
}
