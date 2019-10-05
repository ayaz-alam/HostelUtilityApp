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
        //TODO @AyAZz
    }
}
