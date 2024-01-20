package com.code_base_update.models;

import android.content.Context;

import com.code_base_update.DatabaseManager;
import com.code_base_update.beans.CollegeBean;
import com.code_base_update.interfaces.DataCallback;
import com.code_base_update.presenters.IChooseCollegePresenter;
import com.code_base_update.view.IChooseCollegeView;

import java.util.ArrayList;

public class ChooseCollegeModel implements IChooseCollegePresenter {

    private IChooseCollegeView mView;
    private DatabaseManager mDatabaseManager;
    private ArrayList<CollegeBean> collegeList;

    public ChooseCollegeModel(Context context) {
        mDatabaseManager = new DatabaseManager(context);
    }

    @Override
    public void attachView(IChooseCollegeView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void fetchCollegeList() {
        mView.initiatedFetch();
        mDatabaseManager.loadAllColleges(new DataCallback<ArrayList<CollegeBean>>() {
            @Override
            public void onSuccess(ArrayList<CollegeBean> collegeBeanArrayList) {
                collegeList = collegeBeanArrayList;
                mView.collegeListFetched(collegeBeanArrayList);
            }

            @Override
            public void onFailure(String msg) {
                mView.failed(msg);
            }

            @Override
            public void onError(String msg) {
                mView.failed(msg);
            }
        });


    }

    @Override
    public void fetchHostelList(String collegeId) {

        for(CollegeBean thisCollege: collegeList){
            if(thisCollege.getCollegeId().equals(collegeId)&&thisCollege.getHostels()!=null) {
                mView.hostelListFetched(thisCollege.getHostels(),thisCollege);
                return;
            }
        }
        mView.couldNotFetchHostels();
    }
}
