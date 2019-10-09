package com.code_base_update.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.code_base_update.beans.CollegeBean;
import com.code_base_update.beans.HostelBean;
import com.code_base_update.beans.Student;
import com.code_base_update.interfaces.IChooseCollegeCallback;
import com.code_base_update.models.RegistrationModel;
import com.code_base_update.presenters.IRegistratonPresenter;
import com.code_base_update.ui.dialogs.ChooseCollegeDialog;
import com.code_base_update.utility.InputHelper;
import com.code_base_update.view.IRegistrationView;
import com.medeveloper.ayaz.hostelutility.R;

public class RegistrationActivity extends BaseActivity<IRegistrationView, IRegistratonPresenter> implements IRegistrationView, IChooseCollegeCallback {

    private ChooseCollegeDialog dialog;

    @Override
    protected IRegistratonPresenter createPresenter() {
        return new RegistrationModel();
    }

    @Override
    protected void initViewsAndEvents() {

        dialog = new ChooseCollegeDialog(this,this);

        getView(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    mPresenter.performRegistration(getStudentDetails());
                }
            }
        });

        dialog.show();
    }

    //Get object of student generated from input values
    private Student getStudentDetails() {
        Student student = new Student();
        student.setStudentName(fetchText(R.id.et_name));
        student.setFatherName(fetchText(R.id.et_FatherName));
        student.setEmail(fetchText(R.id.et_email));
        student.setMobileNo(fetchText(R.id.et_mobileNo));
        student.setAdharNo(fetchText(R.id.et_adhar));
        student.setEnrollNo(fetchText(R.id.et_enrollNo));
        student.setWhatsappNo(fetchText(R.id.et_whatsapp_no));
        student.setRoom(fetchText(R.id.et_room));
        student.setAddress(fetchText(R.id.et_address));
        student.setGuardiaName(fetchText(R.id.et_guardian));
        student.setBloodGroup(validateSpinner(R.id.sp_blood).getSelectedItem().toString());
        student.setCategory(validateSpinner(R.id.sp_category).getSelectedItem().toString());
        student.setBranch(validateSpinner(R.id.sp_branch).getSelectedItem().toString());
        student.setClassName(validateSpinner(R.id.sp_class).getSelectedItem().toString());
        student.setYear(validateSpinner(R.id.sp_year).getSelectedItem().toString());
        return student;
    }

    //Checks all the input for validation
    private boolean validateInputs() {

        clearAllErrors();

        if (TextUtils.isEmpty(fetchText(R.id.et_name))) {
            setILError(R.id.input_Sname, "Required");
            getView(R.id.et_name).requestFocus();
        } else if (TextUtils.isEmpty(fetchText(R.id.et_FatherName))) {
            setILError(R.id.input_Fname, "Required");
            getView(R.id.et_FatherName).requestFocus();
        } else if (validateSpinner(R.id.sp_blood).getSelectedItemPosition() == 0) {
            toastMsg("Please select blood group");
        } else if (validateSpinner(R.id.sp_category).getSelectedItemPosition() == 0) {
            toastMsg("Please select Category");
        } else if (TextUtils.isEmpty(fetchText(R.id.et_email))) {
            setILError(R.id.input_email, "Required");
            getView(R.id.et_email).requestFocus();
        } else if (!InputHelper.verifyEMail(fetchText(R.id.et_email))) {
            setILError(R.id.input_email, "Invalid");
            toastMsg("Please Enter valid email");
            getView(R.id.et_email).requestFocus();
        } else if (!InputHelper.verifyMobileNumber(fetchText(R.id.et_mobileNo))) {
            setILError(R.id.input_mobile, "Please enter 10 digit mobile No");
            getView(R.id.et_mobileNo).requestFocus();
        } else if (TextUtils.isEmpty(fetchText(R.id.et_enrollNo))) {
            setILError(R.id.input_enroll, "Required");
            getView(R.id.et_enrollNo).requestFocus();
        } else if (TextUtils.isEmpty(fetchText(R.id.et_adhar))) {
            setILError(R.id.input_aadhar, "Please enter adhar No");
            getView(R.id.et_adhar).requestFocus();
        } else if (!InputHelper.verifyMobileNumber(fetchText(R.id.et_whatsapp_no))) {
            setILError(R.id.input_whatsapp, "Please enter whatsapp No");
            getView(R.id.et_whatsapp_no).requestFocus();
        } else if (TextUtils.isEmpty(fetchText(R.id.et_room))) {
            setILError(R.id.input_room, "Required");
            getView(R.id.et_room).requestFocus();
        } else if (TextUtils.isEmpty(fetchText(R.id.et_address))) {
            setILError(R.id.input_address, "Required");
            getView(R.id.et_address).requestFocus();
        } else if (TextUtils.isEmpty(fetchText(R.id.et_guardian))) {
            setILError(R.id.input_guardian, "Required");
            getView(R.id.et_guardian).requestFocus();
        } else if (validateSpinner(R.id.sp_class).getSelectedItemPosition() == 0) {
            toastMsg("Please select Class");
        } else if (validateSpinner(R.id.sp_year).getSelectedItemPosition() == 0) {
            toastMsg("Please select Year");
        } else if (validateSpinner(R.id.sp_branch).getSelectedItemPosition() == 0) {
            toastMsg("Please select Branch");
        }
        return true;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.new_registration_activity;
    }

    @Override
    public void registrationSuccess() {

    }

    @Override
    public void registrationUnsuccess() {

    }

    @Override
    public void badCredentials() {

    }

    @Override
    public void initiated() {

    }

    @Override
    public void onSuccess(CollegeBean collegeBean, HostelBean hostelBean) {

    }

    @Override
    public void onFailure(String msg) {
        toastMsg("Error: "+msg);
    }
}
