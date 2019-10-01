package com.code_base_update.models;


import androidx.annotation.NonNull;

import com.code_base_update.presenters.IForgotPasswordPresenter;
import com.code_base_update.view.IForgotPasswordView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordModel implements IForgotPasswordPresenter {

    private IForgotPasswordView mView;
    @Override
    public void instantiateProcess(String email) {
        mView.sendingMailRequest();
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            mView.sentSuccessfully();
                        else mView.onErrorOccurred(task.getException().getLocalizedMessage());
                    }
                });
    }

    @Override
    public void attachView(IForgotPasswordView view) {
        this.mView =view;
    }

    @Override
    public void detachView() {

    }
}
