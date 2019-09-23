package com.code_base_update.models;

import java.util.ArrayList;

import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.presenters.IComplaintToModelPresenter;
import com.code_base_update.presenters.IModelToComplaintPresenter;

public class ComplaintModel implements IComplaintToModelPresenter {
    private IModelToComplaintPresenter presenter;

    public void setPresenter(IModelToComplaintPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDomainSelected(int id) {
        ArrayList<String> subDomainList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            subDomainList.add("Sub Domain " + (i + 1));
        }
        //TODO fetch SubDomain list
        presenter.onSubDomainLoaded(subDomainList);
    }

    @Override
    public void onSubDomainSelected(String subDomain) {
        ArrayList<String> descriptionList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            descriptionList.add("Description " + (i + 1));
        }
        //TODO fetch Description list
        presenter.onDescriptionLoaded(descriptionList);
    }

    @Override
    public void registerComplaint(ComplaintBean complaintToRegister) {
        int statusCode = -1;
        //TODO Register complaint to database and store result in status code
        presenter.registrationStatus(statusCode);
    }
}
