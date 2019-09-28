package com.code_base_update.models;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.code_base_update.presenters.ILoginPresenter;
import com.code_base_update.view.ILoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.medeveloper.ayaz.hostelutility.Registration.Registration;

public class LoginModel implements ILoginPresenter {

    private ILoginView loginView;
    private FirebaseAuth mAuth;

    public LoginModel(){
        mAuth =FirebaseAuth.getInstance();
    }

    @Override
    public void performLogin(String username, String password, int userType) {
        loginView.onLoginInitiated();
        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                    loginView.onLoginSuccess();
                else loginView.onLoginFailure(task.getException().getMessage());
            }
        });

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
