package com.code_base_update.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.code_base_update.beans.Student;
import com.code_base_update.models.RegistrationModel;
import com.code_base_update.presenters.IRegistratonPresenter;
import com.code_base_update.utility.InputHelper;
import com.code_base_update.view.IRegistrationView;
import com.medeveloper.ayaz.hostelutility.R;

public class RegistrationActivity extends BaseActivity<IRegistrationView, IRegistratonPresenter> implements IRegistrationView {

    @Override
    protected IRegistratonPresenter createPresenter() {
        return new RegistrationModel();
    }

    @Override
    protected void initViewsAndEvents() {
        //TODO @Kanika Use this in place of onCreate

        getView(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInputs()){
                    mPresenter.performRegistration(getStudentDetails());
                }
            }
        });




    }

    //Get object of student generated from input values
    private Student getStudentDetails() {

        if(TextUtils.isEmpty(fetchText(R.id.et_name))){
            setILError(R.id.input_Sname,"Required");
            getView(R.id.et_name).requestFocus();
        }

        if(TextUtils.isEmpty(fetchText(R.id.et_email))){
            setILError(R.id.input_Sname,"Required");
            getView(R.id.et_name).requestFocus();
        }else if(InputHelper.verifyEMail(fetchText(R.id.et_email))){
            setILError(R.id.input_Sname,"Invalid");
            getView(R.id.et_name).requestFocus();
        }



        return null;
    }

    //Checks all the input for validation
    private boolean validateInputs() {


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
}
