package com.code_base_update.models;

import com.code_base_update.utility.UserManager;
import com.code_base_update.interfaces.SuccessCallback;
import com.code_base_update.presenters.IChangePasswordPresenter;
import com.code_base_update.view.IChangePasswordView;

public class ChangePasswordModel implements IChangePasswordPresenter {

    private IChangePasswordView view;
    private UserManager userManager;

    public ChangePasswordModel() {
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
    public void checkAuthentication(String oldPassword, String newPassword, String confirmPassword) {

        if (newPassword.equals(confirmPassword)) {

            userManager.reAuthenticateUser(new SuccessCallback() {
                @Override
                public void onInitiated() {
                    view.processInitiated();
                }

                @Override
                public void onSuccess() {
                    view.passwordChangedSuccessfully();
                }

                @Override
                public void onFailure(String msg) {
                    view.passwordNotChanged(msg);
                }
            }, userManager.getEmail(), oldPassword, newPassword);
        } else {
            view.passwordDoNotMatch();
        }
    }
}
