package com.code_base_update.presenters;


import android.content.Context;

import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.view.IApplicationView;

public interface IApplicationPresenter extends IBasePresenter<IApplicationView> {
    void saveApplication(ApplicationBean application, Context context);
}
