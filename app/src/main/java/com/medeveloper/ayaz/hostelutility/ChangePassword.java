package com.medeveloper.ayaz.hostelutility;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class ChangePassword extends AppCompatActivity {

    EditText oldPass,newPass,confirmPass;
    Button changePass;
    FirebaseAuth mAuth;
    SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.change_password);
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

        mAuth=FirebaseAuth.getInstance();

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                if(isOkay())
                    checkAuthentication();
            }
        });



    }

    private void checkAuthentication() {
        pDialog.show();
        final String Email=mAuth.getCurrentUser().getEmail();
        final String newPassword=newPass.getText().toString();
        String confirmPassword=confirmPass.getText().toString();
        final String currentPassword=oldPass.getText().toString();
        if(newPassword.equals(confirmPassword))
        {


            AuthCredential credential= EmailAuthProvider.getCredential(Email,currentPassword);
            mAuth.getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {

                        mAuth.getCurrentUser().updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    pDialog.dismiss();
                                    SweetAlertDialog sDialog = new SweetAlertDialog(ChangePassword.this, SweetAlertDialog.SUCCESS_TYPE);
                                    sDialog.setCancelable(false);
                                    sDialog.setTitleText("Password Changed").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            finish();
                                        }
                                    });
                                    sDialog.show();
                                    Toast.makeText(getApplicationContext(),"Changed",Toast.LENGTH_SHORT).show();

                                }
                                else
                                {
                                pDialog.dismiss();

                                    Toast.makeText(getApplicationContext(),"Can't Changed "+task.getException(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                    else
                    {
                        pDialog.dismiss();
                        final SweetAlertDialog sDialog=new SweetAlertDialog(ChangePassword.this,SweetAlertDialog.ERROR_TYPE);
                        sDialog.setCancelable(false);
                        sDialog.setTitleText("Can't change password").setContentText(task.getException().getLocalizedMessage()).
                                setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sDialog.dismiss();
                            }
                        });
                        sDialog.show();

                    }

                }
            });


        }
        else{
            pDialog.dismiss();
            final SweetAlertDialog sDialog=new SweetAlertDialog(ChangePassword.this,SweetAlertDialog.ERROR_TYPE);
            sDialog.setCancelable(false);
            sDialog.setTitleText("Password doesn't match").setContentText("Please input the new password and confirm it,").
                    setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    });
                    sDialog.show();
        }
    }


    private boolean isOkay() {

        boolean isOkay=true;
        oldPass.setError(null);
        newPass.setError(null);
        confirmPass.setError(null);

        if(oldPass.getText().toString().equals(""))
        {
            oldPass.setError("Please enter your old password");
            oldPass.requestFocus();
            isOkay=false;

        }
        else if(newPass.getText().toString().equals(""))
        {
            newPass.setError("Please enter your new password");
            newPass.requestFocus();
            isOkay=false;
        }
        else if(confirmPass.getText().toString().equals(""))
        {
            confirmPass.setError("Please confirm your password");
            confirmPass.requestFocus();
            isOkay=false;
        }

        return isOkay;
    }
}
