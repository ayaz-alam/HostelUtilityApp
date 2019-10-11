package com.code_base_update.models;

import android.content.Context;

import com.code_base_update.Constants;
import com.code_base_update.DatabaseManager;
import com.code_base_update.beans.CollegeBean;
import com.code_base_update.beans.HostelBean;
import com.code_base_update.beans.Student;
import com.code_base_update.interfaces.SuccessCallback;
import com.code_base_update.presenters.IRegistratonPresenter;
import com.code_base_update.utility.UserManager;
import com.code_base_update.view.IRegistrationView;

public class RegistrationModel implements IRegistratonPresenter {
    private IRegistrationView view;
    private DatabaseManager databaseManager;


    public RegistrationModel(Context context) {
        databaseManager = new DatabaseManager(context);
    }

    @Override
    public void attachView(IRegistrationView view) {
        this.view = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void performRegistration(final Student studentDetails, CollegeBean collegeBean, HostelBean hostelBean) {
        view.initiated();
        UserManager userManager = new UserManager();
       isUserPresentInDatabase(studentDetails, new SuccessCallback() {

            @Override
            public void onInitiated() {

            }

            @Override
            public void onSuccess() {
                //TODO create authentication then proceed
                databaseManager.registerStudent(studentDetails);
            }

            @Override
            public void onFailure(String msg) {

            }
        });


    }


    private void isUserPresentInDatabase(Student studentDetails,SuccessCallback callback) {
        callback.onInitiated();
        callback.onSuccess();
        callback.onFailure("");
    }
}