package com.code_base_update.view;

public interface IForgotPasswordView extends IBaseView {
    void sendingMailRequest();
    void sentSuccessfully();
    void onErrorOccurred(String msg);
}
