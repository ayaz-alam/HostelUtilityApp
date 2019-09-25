package com.code_base_update.view;

public interface ILoginView extends IBaseView {
    void onLoginInitiated();
    void onLoginSuccess();
    void onLoginFailure(int errorCode);
    void onBadCredential(int errorCode);
}
