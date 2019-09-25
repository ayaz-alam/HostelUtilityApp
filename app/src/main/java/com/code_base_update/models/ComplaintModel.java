package com.code_base_update.models;

import java.util.ArrayList;

import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.view.IComplaintView;
import com.code_base_update.presenters.IComplaintPresenter;

public class ComplaintModel implements IComplaintPresenter {

    private IComplaintView complaintView;

    public ComplaintModel(){
    }

    @Override
    public void onDomainSelected(int id) {
        ArrayList<String> subDomainList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            subDomainList.add("Sub Domain " + (i + 1));
        }
        //TODO fetch SubDomain list
        complaintView.onSubDomainLoaded(subDomainList);
    }

    @Override
    public void onSubDomainSelected(String subDomain) {
        ArrayList<String> descriptionList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            descriptionList.add("Description " + (i + 1));
        }
        //TODO fetch Description list
        complaintView.onDescriptionLoaded(descriptionList);
    }

    @Override
    public void registerComplaint(ComplaintBean complaintToRegister) {
        int statusCode = -1;
        //TODO Register complaint to database and store result in status code
        complaintView.registrationStatus(statusCode);
    }

    @Override
    public void attachView(IComplaintView view) {
        this.complaintView = view;
    }

    @Override
    public void detachView() {
        this.complaintView = null;
    }
}
