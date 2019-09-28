package com.medeveloper.ayaz.hostelutility;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.code_base_update.models.ChangePasswordModel;
import com.code_base_update.presenters.IBasePresenter;
import com.code_base_update.presenters.IChangePasswordPresenter;
import com.code_base_update.ui.BaseActivity;
import com.code_base_update.view.IChangePasswordView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class ChangePassword extends BaseActivity<IChangePasswordView, IChangePasswordPresenter> implements IChangePasswordView{

    EditText oldPass,newPass,confirmPass;
    Button changePass;

    SweetAlertDialog pDialog;

    @Override
    protected IChangePasswordPresenter createPresenter() {
        return new ChangePasswordModel();
    }

    @Override
    protected void initViewsAndEvents() {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        oldPass=findViewById(R.id.old_password);
        newPass=findViewById(R.id.new_password);
        confirmPass=findViewById(R.id.confirm_password);
        changePass=findViewById(R.id.change_password);
        pDialog=new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Please wait...");

        findViewById(R.id.outer_area).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOkay()){

                    String email = ((EditText)getView(R.id.old_password)).getText().toString();
                    String password = ((EditText)getView(R.id.old_password)).getText().toString();

                    String newPassword= ((EditText)getView(R.id.old_password)).getText().toString();
                    String confirmPassword = ((EditText)getView(R.id.old_password)).getText().toString();

                    mPresenter.checkAuthentication(email,password,newPassword,confirmPassword);
                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.change_password;
    }

    private boolean isOkay() {

        boolean isOkay=true;
        oldPass.setError(null);
        newPass.setError(null);
        confirmPass.setError(null);

        if(oldPass.getText().toString().equals("")){
            oldPass.setError("Please enter your old password");
            oldPass.requestFocus();
            isOkay=false;

        }
        else if(newPass.getText().toString().equals("")){
            newPass.setError("Please enter your new password");
            newPass.requestFocus();
            isOkay=false;
        }
        else if(confirmPass.getText().toString().equals("")){
            confirmPass.setError("Please confirm your password");
            confirmPass.requestFocus();
            isOkay=false;
        }

        return isOkay;
    }

    @Override
    public void passwordDoNotMatch() {
        Toast.makeText(this,"Password do not match",Toast.LENGTH_LONG).show();
    }

    @Override
    public void passwordChangedSuccessfully() {
        Toast.makeText(this,"Password changed successfully",Toast.LENGTH_LONG).show();
    }

    @Override
    public void passwordNotChanged(String msg) {
        Toast.makeText(this,"Password not changed "+msg,Toast.LENGTH_LONG).show();
    }
}
