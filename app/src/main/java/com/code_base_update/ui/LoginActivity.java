package com.code_base_update.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import guest_module.GuestDashboard;
import officials_module.ui.OfficialDashboard;

import com.code_base_update.interfaces.SuccessCallback;
import com.code_base_update.utility.UserManager;
import com.code_base_update.utility.InputHelper;
import com.code_base_update.models.LoginModel;
import com.code_base_update.presenters.ILoginPresenter;
import com.code_base_update.view.ILoginView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.medeveloper.ayaz.hostelutility.R;

import static com.code_base_update.Constants.STUDENT;
import static com.code_base_update.Constants.TEACHER;

public class LoginActivity extends BaseActivity<ILoginView, ILoginPresenter> implements ILoginView{

    private ProgressDialog mProgressDialog;
    private EditText mUsername,mPassword;
    private Context mCtx;

    @Override
    protected ILoginPresenter createPresenter() {
        return new LoginModel(this);
    }

    @Override
    protected void initViewsAndEvents() {
        mCtx =this;
        //TODO change progressDialog with custom dialog
        mProgressDialog = new MyDialog().getProgressDialog("Please wait..",mCtx);
        mUsername = (TextInputEditText)getView(R.id.et_user_name);
        mPassword = (TextInputEditText)getView(R.id.et_password);
        final Button mLoginButton = (Button)getView(R.id.btn_login);
        final RadioGroup rgLoginAs = (RadioGroup)getView(R.id.rg_loginAs);
        final TextInputLayout mUsernameLayout = (TextInputLayout)getView(R.id.txtInputUsername);
        final TextInputLayout mPasswordLayout = (TextInputLayout)getView(R.id.txtInputPassword);
        getUserManager().setMVHostel(this);

        if(getUserManager().isUserLoggedIn()){
            onLoginSuccess(getUserManager().getUserType(mContext));
            finishAffinity();
            return;
        }

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsernameLayout.setError(null);
                mPasswordLayout.setError(null);
                String userName= mUsername.getText().toString();
                String password = mPassword.getText().toString();
                if(!InputHelper.verifyEMail(userName)) mUsernameLayout.setError("Invalid email address");
                else if(InputHelper.verifyInputField(password,6)==InputHelper.EMPTY_FIELD)
                    mPasswordLayout.setError("Required");
                else if(InputHelper.verifyInputField(password,6)==InputHelper.SHORT_LENGTH)
                    mPasswordLayout.setError("Length should be at least "+6);
                else {
                    mPresenter.performLogin(mUsername.getText().toString(),
                        mPassword.getText().toString(),
                        rgLoginAs.getCheckedRadioButtonId() == R.id.rb_student ? STUDENT :TEACHER);
                }
            }
        });

        getView(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.startSignUpActivity(mCtx);
            }
        });
        getView(R.id.btn_forgot_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.startForgotPassWordDialog(mCtx);
            }
        });
        getView(R.id.btn_guest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, GuestDashboard.class);//Optional parameters
                startActivity(myIntent);
            }
        });




    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_login_activity;
    }

    @Override
    public void onLoginInitiated() {
        mProgressDialog.show();
    }

    @Override
    public void onLoginSuccess(int UserType) {
        mProgressDialog.dismiss();
        if(UserType==STUDENT)
            startActivity(new Intent(this, Dashboard.class));
        else if(UserType==TEACHER)
            startActivity(new Intent(this, OfficialDashboard.class));
        finish();
    }

    @Override
    public void onLoginFailure(String error) {
        mProgressDialog.dismiss();
        Toast.makeText(this,"Error: "+error,Toast.LENGTH_LONG).show();
        getUserManager().logout(this);

    }

    @Override
    public void onBadCredential(String error) {
        mProgressDialog.dismiss();
        Toast.makeText(this,"Error: "+error,Toast.LENGTH_LONG).show();
        getUserManager().logout(this);
    }
}
