package com.code_base_update.presenters;


import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.view.IComplaintView;

public interface IComplaintPresenter extends IBasePresenter<IComplaintView>{
    void onDomainSelected(int id);

    void onSubDomainSelected(String subDomain);

    void registerComplaint(ComplaintBean complaintToRegister);
}
