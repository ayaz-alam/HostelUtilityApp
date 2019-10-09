package com.code_base_update.models;

import com.code_base_update.beans.Student;
import com.code_base_update.interfaces.SimpleCallback;
import com.code_base_update.interfaces.SuccessCallback;
import com.code_base_update.presenters.IRegistratonPresenter;
import com.code_base_update.utility.UserManager;
import com.code_base_update.view.IRegistrationView;

public class RegistrationModel implements IRegistratonPresenter {
    private IRegistrationView view;


    @Override
    public void attachView(IRegistrationView view) {
        this.view = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void performRegistration(Student studentDetails) {
        view.initiated();
        UserManager userManager = new UserManager();
       isUserPresentInDatabase(studentDetails, new SuccessCallback() {

            @Override
            public void onInitiated() {

            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });


    }


    private boolean isUserPresentInDatabase(Student studentDetails,SuccessCallback callback) {




        return true;
    }
}