package com.code_base_update.presenters;


import com.code_base_update.beans.ComplaintBean;

public interface IComplaintToModelPresenter {
    void onDomainSelected(int id);

    void onSubDomainSelected(String subDomain);

    void registerComplaint(ComplaintBean complaintToRegister);
}
