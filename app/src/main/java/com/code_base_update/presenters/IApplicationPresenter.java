package com.code_base_update.presenters;


import com.code_base_update.beans.ApplicationBean;
import com.code_base_update.view.IApplicationView;

public interface IApplicationPresenter extends IBasePresenter<IApplicationView> {
    void saveApplication(ApplicationBean application);
}
