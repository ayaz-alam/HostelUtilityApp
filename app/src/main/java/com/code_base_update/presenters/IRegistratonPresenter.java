package com.code_base_update.presenters;

import com.code_base_update.beans.Student;
import com.code_base_update.view.IRegistrationView;

public interface IRegistratonPresenter extends IBasePresenter<IRegistrationView> {

    void performRegistration(Student studentDetails);
}
