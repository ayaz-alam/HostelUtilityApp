package com.code_base_update.models;

import androidx.annotation.NonNull;

import com.code_base_update.UserManager;
import com.code_base_update.interfaces.SuccessCallback;
import com.code_base_update.presenters.IChangePasswordPresenter;
import com.code_base_update.view.IChangePasswordView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ChangePasswordModel implements IChangePasswordPresenter {

    private IChangePasswordView view;
    private UserManager userManager;

    public ChangePasswordModel(){
        userManager = new UserManager();
    }

    @Override
    public void attachView(IChangePasswordView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void checkAuthentication(String email, String oldPassword, String newPassword, String confirmPassword) {

        if(newPassword.equals(confirmPassword)){

            userManager.reAuthenticateUser(new SuccessCallback(){
                @Override
                public void onInitiated() {

                }

                @Override
                public void onSuccess() {
                    view.passwordChangedSuccessfully();
                }

                @Override
                public void onFailure(String msg) {
                    view.passwordNotChanged(msg);
                }
            },email,oldPassword,newPassword);
        }
        else{
            view.passwordDoNotMatch();
        }
    }
}
