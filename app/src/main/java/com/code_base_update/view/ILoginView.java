package com.code_base_update.view;

public interface ILoginView extends IBaseView {
    void onLoginInitiated();
    void onLoginSuccess(int userType);
    void onLoginFailure(String error);
    void onBadCredential(String errorCode);
}
