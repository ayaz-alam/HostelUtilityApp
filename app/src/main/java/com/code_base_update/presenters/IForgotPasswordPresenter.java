package com.code_base_update.presenters;

import com.code_base_update.view.IForgotPasswordView;

public interface IForgotPasswordPresenter extends IBasePresenter<IForgotPasswordView> {
    void instantiateProcess(String email);
}
