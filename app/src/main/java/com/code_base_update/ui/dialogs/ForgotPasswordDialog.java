package com.code_base_update.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import com.code_base_update.utility.InputHelper;
import com.code_base_update.models.ForgotPasswordModel;
import com.code_base_update.presenters.IForgotPasswordPresenter;
import com.code_base_update.view.IForgotPasswordView;
import com.google.android.material.textfield.TextInputLayout;
import com.medeveloper.ayaz.hostelutility.R;

public class ForgotPasswordDialog extends Dialog implements IForgotPasswordView {

    private EditText etEmailText;
    private Group baseView;
    private ProgressBar pgProgressbar;
    private TextView tvSuccessText;
    private IForgotPasswordPresenter mPresenter;

    public ForgotPasswordDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.new_dialog_forgot_pass);
        prepareView();
        mPresenter = new ForgotPasswordModel();
        mPresenter.attachView(this);
    }

    private void prepareView() {
        etEmailText = findViewById(R.id.et_email);
        pgProgressbar = findViewById(R.id.pg_forgot_password);
        tvSuccessText = findViewById(R.id.pg_success_text);
        baseView = findViewById(R.id.editTextGroup);
        pgProgressbar.setVisibility(View.GONE);
        tvSuccessText.setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_forgot_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmailText.getText().toString();
                if (TextUtils.isEmpty(email))
                    ((TextInputLayout) findViewById(R.id.textInputLayout)).setError("Required");
                else if (!InputHelper.verifyEMail(email))
                    ((TextInputLayout) findViewById(R.id.textInputLayout)).setError("Invalid email");
                else
                    mPresenter.instantiateProcess(email);
            }
        });
    }

    @Override
    public void sendingMailRequest() {
        baseView.setVisibility(View.INVISIBLE);
        pgProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void sentSuccessfully() {
        pgProgressbar.setVisibility(View.GONE);
        tvSuccessText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorOccurred(String msg) {
        pgProgressbar.setVisibility(View.GONE);
        baseView.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), "Error: " + msg, Toast.LENGTH_LONG).show();
    }
}
