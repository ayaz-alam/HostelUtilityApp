package com.code_base_update.view;

public interface IChangePasswordView extends IBaseView {

    void passwordDoNotMatch();

    void passwordChangedSuccessfully();

    void passwordNotChanged(String msg);

    void processInitiated();

}
