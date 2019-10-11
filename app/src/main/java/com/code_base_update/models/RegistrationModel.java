package com.code_base_update.models;

import android.content.Context;

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
    private UserManager userManager;
    private Context mContext;



    public RegistrationModel(Context context) {
        databaseManager = new DatabaseManager(context);
        userManager = new UserManager();
        mContext = context;
    }

    @Override
    public void attachView(IRegistrationView view) {
        this.view = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void performRegistration(final Student studentDetails) {
        view.initiated();
       isUserPresentInDatabase(studentDetails, new SuccessCallback() {

            @Override
            public void onInitiated() {
                //Set the UI to initiated state
                view.initiated();
            }

            @Override
            public void onSuccess() {
                createUser(studentDetails);
            }

            @Override
            public void onFailure(String msg) {
                view.registrationFailed(msg);
            }
        });


    }

    private void createUser(final Student studentDetails) {

        userManager.createUser(studentDetails, new SuccessCallback() {
            @Override
            public void onInitiated() {
                //Don't do anything, UI already in initiated state
            }

            @Override
            public void onSuccess() {
                registerStudent(studentDetails);
            }

            @Override
            public void onFailure(String msg) {
                view.registrationFailed(msg);
            }
        });
    }

    private void registerStudent(final Student studentDetails) {

        databaseManager.registerStudent(studentDetails, new SuccessCallback(){
            @Override
            public void onInitiated() {
                //Don't do anything, UI already in initiated state
            }

            @Override
            public void onSuccess() {
                databaseManager.saveStudent(studentDetails);
                view.registrationSuccess();
                userManager.setStudent(studentDetails,mContext);
            }

            @Override
            public void onFailure(String msg) {
                view.registrationFailed(msg);
            }
        });

    }


    private void isUserPresentInDatabase(Student studentDetails,SuccessCallback callback) {
        databaseManager.isStudentEnrolled(studentDetails,callback);
    }
}