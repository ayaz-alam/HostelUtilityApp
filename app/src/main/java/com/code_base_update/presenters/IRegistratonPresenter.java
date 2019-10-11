package com.code_base_update.presenters;

import com.code_base_update.beans.CollegeBean;
import com.code_base_update.beans.HostelBean;
import com.code_base_update.beans.Student;
import com.code_base_update.view.IRegistrationView;

public interface IRegistratonPresenter extends IBasePresenter<IRegistrationView> {

    void performRegistration(Student studentDetails, CollegeBean collegeBean, HostelBean hostelBean);
}
