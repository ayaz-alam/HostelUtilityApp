package com.code_base_update.presenters;

import com.code_base_update.view.IChangePasswordView;

public interface IChangePasswordPresenter extends IBasePresenter<IChangePasswordView> {
    void checkAuthentication(String oldPassword, String newOldPass, String confirmPassword);
}
