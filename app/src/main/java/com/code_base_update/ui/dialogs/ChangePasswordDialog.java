package com.code_base_update.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.code_base_update.utility.InputHelper;
import com.code_base_update.models.ChangePasswordModel;
import com.code_base_update.presenters.IChangePasswordPresenter;
import com.code_base_update.view.IChangePasswordView;
import com.google.android.material.textfield.TextInputLayout;
import com.medeveloper.ayaz.hostelutility.R;

public class ChangePasswordDialog extends Dialog implements IChangePasswordView {

    private IChangePasswordPresenter mPresenter;

    public ChangePasswordDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.new_dialog_change_pass);
        prepareView();

        mPresenter = new ChangePasswordModel();

        mPresenter.attachView(this);
    }

    private void prepareView() {
        findViewById(R.id.btn_change_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput())
                    mPresenter.checkAuthentication(getOldPass(), getNewPass(), getConfirmPass());
            }
        });

    }

    private String getConfirmPass() {
        return ((EditText) findViewById(R.id.et_confirm_password)).getText().toString();
    }

    private String getNewPass() {
        return ((EditText) findViewById(R.id.et_new_password)).getText().toString();
    }

    private String getOldPass() {
        return ((EditText) findViewById(R.id.et_old_password)).getText().toString();
    }

    private boolean validateInput() {

        ((TextInputLayout) findViewById(R.id.input_old_pass)).setError(null);

        ((TextInputLayout) findViewById(R.id.input_new_pass)).setError(null);

        ((TextInputLayout) findViewById(R.id.input_new_pass2)).setError(null);

        String oldPass = getOldPass();
        String newPass = getNewPass();
        String confirm = getConfirmPass();
        if (!InputHelper.checkPassword(oldPass)) {
            ((TextInputLayout) findViewById(R.id.input_old_pass)).setError("Invalid");
            return false;
        }

        if (!InputHelper.checkPassword(newPass)) {
            ((TextInputLayout) findViewById(R.id.input_new_pass)).setError("Invalid");
            return false;
        }

        if (!InputHelper.checkPassword(confirm)) {
            ((TextInputLayout) findViewById(R.id.input_new_pass2)).setError("Invalid");
            return false;
        }

        return true;
    }

    @Override
    public void passwordDoNotMatch() {
        ((TextInputLayout) findViewById(R.id.input_new_pass2)).setError("Password do not match");
    }

    @Override
    public void passwordChangedSuccessfully() {
        Toast.makeText(getContext(), "Password changed successfully", Toast.LENGTH_LONG).show();
        dismiss();
    }

    @Override
    public void passwordNotChanged(String msg) {
        Toast.makeText(getContext(), "Failed " + msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void processInitiated() {
        Toast.makeText(getContext(), "Please wait..", Toast.LENGTH_LONG).show();
    }
}
