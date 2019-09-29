package com.code_base_update.models;

import android.content.Context;

import java.util.ArrayList;

import com.code_base_update.DatabaseManager;
import com.code_base_update.beans.ComplaintBean;
import com.code_base_update.interfaces.DataCallback;
import com.code_base_update.interfaces.SuccessCallback;
import com.code_base_update.view.IComplaintView;
import com.code_base_update.presenters.IComplaintPresenter;

public class ComplaintModel implements IComplaintPresenter {

    private final Context context;
    private IComplaintView complaintView;
    private DatabaseManager databaseManager;

    public ComplaintModel(Context context){
        this.context =context;
        databaseManager = new DatabaseManager(context);
    }

    @Override
    public void loadDomain() {

        DataCallback<ArrayList<String>> dataCallback =new DataCallback<ArrayList<String>>() {
            @Override
            public void onSuccess(ArrayList<String> list) {
                complaintView.onDomainLoaded(list);
            }

            @Override
            public void onFailure(String msg) {
                complaintView.domainLoadError(msg);
            }

            @Override
            public void onError(String msg) {
                complaintView.domainLoadError(msg);
            }
        };
        databaseManager.loadComplaintTypes(dataCallback);

    }

    @Override
    public void onDomainSelected(String domain) {
        DataCallback<ArrayList<String>> dataCallback =new DataCallback<ArrayList<String>>() {
            @Override
            public void onSuccess(ArrayList<String> strings) {
                complaintView.onSubDomainLoaded(strings);
            }

            @Override
            public void onFailure(String msg) {
                complaintView.subdomainLoadError(msg);
            }

            @Override
            public void onError(String msg) {
                complaintView.subdomainLoadError(msg);
            }
        };

        databaseManager.loadSubDomains(domain,dataCallback);
    }

    @Override
    public void registerComplaint(final ComplaintBean complaintToRegister) {


        new DatabaseManager(context).registerComplaint(new SuccessCallback(){
            @Override
            public void onInitiated() {
                complaintView.registrationStarted();
            }

            @Override
            public void onSuccess() {
                complaintView.registeredSuccessfully();
            }

            @Override
            public void onFailure(String msg) {
                complaintView.registrationFailed("Failed: "+msg);

            }
        },complaintToRegister);
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
