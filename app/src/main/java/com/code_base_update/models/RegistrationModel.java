package com.code_base_update.models;

import com.code_base_update.beans.Student;
import com.code_base_update.presenters.IRegistratonPresenter;
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
        //Todo
        //Step 1: Check if user is authentic using email address and adhaar number
        //Step 2: if step 1 successfull, generate Credentials using createUserUsingEmail
        //Step 3: Open loginActivity
    }
}
