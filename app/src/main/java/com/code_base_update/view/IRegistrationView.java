package com.code_base_update.view;

public interface IRegistrationView extends IBaseView {
    //TODO  @Kanika define functions to handle UI like
    void registrationSuccess();
    void registrationFailed(String msg);
    void badCredentials();
    void initiated();
}
