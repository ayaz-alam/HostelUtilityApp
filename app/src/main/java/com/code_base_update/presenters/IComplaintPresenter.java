package com.code_base_update.presenters;


import android.content.Context;

import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.view.IComplaintView;

public interface IComplaintPresenter extends IBasePresenter<IComplaintView>{
    void loadDomain();
    void onDomainSelected(String domain);

    void registerComplaint(ComplaintBean complaintToRegister);
}
