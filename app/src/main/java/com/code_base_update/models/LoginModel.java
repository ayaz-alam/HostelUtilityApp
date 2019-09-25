package com.code_base_update.models;

import android.content.Context;
import android.content.Intent;

import com.code_base_update.presenters.ILoginPresenter;
import com.code_base_update.view.ILoginView;
import com.medeveloper.ayaz.hostelutility.Registration.Registration;

public class LoginModel implements ILoginPresenter {

    private ILoginView loginView;
    @Override
    public void performLogin(String username, String password, int userType) {
        loginView.onLoginInitiated();
        /**
         * if(success) return loginView.onLoginSuccess();
         * else return loginView.onLogin
         *
         * */




    }

    @Override
    public void startSignUpActivity(Context mCtx) {
        mCtx.startActivity(new Intent(mCtx, Registration.class));
    }

    @Override
    public void startForgotPassWordActivity(Context mCtx) {

    }

    @Override
    public void attachView(ILoginView view) {
        this.loginView = view;
    }
    @Override
    public void detachView() {
        this.loginView =null;
    }
}
