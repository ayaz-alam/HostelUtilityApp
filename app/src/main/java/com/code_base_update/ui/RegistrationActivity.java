package com.code_base_update.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

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
    private CollegeBean collegeBean;
    private HostelBean hostelBean;
    private ProgressDialog progressDialog;

    @Override
    protected IRegistratonPresenter createPresenter() {
        return new RegistrationModel(this);
    }

    @Override
    protected void initViewsAndEvents() {

        dialog = new ChooseCollegeDialog(this,this);

        progressDialog = new MyDialog().getProgressDialog("Please wait...",this);

        getView(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    Student student = getStudentDetails();
                    student.setHostelId(hostelBean.getHostelId());
                    student.setCollegeId(collegeBean.getCollegeId());
                    mPresenter.performRegistration(student);
                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    //Get object of student generated from input values
    private Student getStudentDetails() {
        Student student = new Student();
        student.setName(fetchText(R.id.et_name));
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
            return false;
        } else if (TextUtils.isEmpty(fetchText(R.id.et_FatherName))) {
            setILError(R.id.input_Fname, "Required");
            getView(R.id.et_FatherName).requestFocus();
            return false;
        } else if (validateSpinner(R.id.sp_blood).getSelectedItemPosition() == 0) {
            toastMsg("Please select blood group");
            return false;
        } else if (validateSpinner(R.id.sp_category).getSelectedItemPosition() == 0) {
            toastMsg("Please select Category");
            return false;
        } else if (TextUtils.isEmpty(fetchText(R.id.et_email))) {
            setILError(R.id.input_email, "Required");
            getView(R.id.et_email).requestFocus();
            return false;
        } else if (!InputHelper.verifyEMail(fetchText(R.id.et_email))) {
            setILError(R.id.input_email, "Invalid");
            toastMsg("Please Enter valid email");
            getView(R.id.et_email).requestFocus();
            return false;
        } else if (!InputHelper.verifyMobileNumber(fetchText(R.id.et_mobileNo))) {
            setILError(R.id.input_mobile, "Please enter 10 digit mobile No");
            getView(R.id.et_mobileNo).requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fetchText(R.id.et_enrollNo))) {
            setILError(R.id.input_enroll, "Required");
            getView(R.id.et_enrollNo).requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fetchText(R.id.et_adhar))) {
            setILError(R.id.input_aadhar, "Please enter adhar No");
            getView(R.id.et_adhar).requestFocus();
            return false;
        } else if (!InputHelper.verifyMobileNumber(fetchText(R.id.et_whatsapp_no))) {
            setILError(R.id.input_whatsapp, "Please enter whatsapp No");
            getView(R.id.et_whatsapp_no).requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fetchText(R.id.et_room))) {
            setILError(R.id.input_room, "Required");
            getView(R.id.et_room).requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fetchText(R.id.et_address))) {
            setILError(R.id.input_address, "Required");
            getView(R.id.et_address).requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fetchText(R.id.et_guardian))) {
            setILError(R.id.input_guardian, "Required");
            getView(R.id.et_guardian).requestFocus();
        } else if (validateSpinner(R.id.sp_class).getSelectedItemPosition() == 0) {
            toastMsg("Please select Class");
        } else if (validateSpinner(R.id.sp_year).getSelectedItemPosition() == 0) {
            toastMsg("Please select Year");
            return false;
        } else if (validateSpinner(R.id.sp_branch).getSelectedItemPosition() == 0) {
            toastMsg("Please select Branch");
            return false;
        }
        return true;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.new_registration_activity;
    }

    @Override
    public void registrationSuccess() {
        toastMsg("Successfully completed");
        getSession().setCollege(collegeBean);
        getSession().setHostel(hostelBean);
        startActivity(new Intent(this,Dashboard.class));
        finishAffinity();
    }

    @Override
    public void registrationFailed(String msg) {
        toastMsg("Incomplete: "+msg);
        progressDialog.dismiss();
        getUserManager().logout();
    }

    @Override
    public void badCredentials() {
        progressDialog.dismiss();
        getUserManager().logout();
        toastMsg("Bad credentials");
    }

    @Override
    public void initiated() {
        progressDialog.show();
    }

    @Override
    public void onSuccess(CollegeBean collegeBean, HostelBean hostelBean) {
        this.collegeBean = collegeBean;
        this.hostelBean = hostelBean;
        setupToolbar(hostelBean.getHostelName()+" Registration");
        getView(R.id.et_name).requestFocus();
    }

    @Override
    public void onFailure(String msg) {
        progressDialog.dismiss();
        toastMsg("Error: "+msg);
        getUserManager().logout();
    }
}
