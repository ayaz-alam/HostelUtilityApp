package com.code_base_update.interfaces;

public interface SuccessCallback {
    void onInitiated();
    void onSuccess();
    void onFailure(String msg);
}
