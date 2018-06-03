package com.medeveloper.ayaz.hostelutility;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.OfficialID;
import com.medeveloper.ayaz.hostelutility.student.Home;
import com.medeveloper.ayaz.hostelutility.student.StudentForm;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class Splash extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 1500;
    private static final int REQUEST_CODE = 222;

    static {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    private int MY_CAMERA_REQUEST_CODE=111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        isPermissionGranted();


        FirebaseAuth.getInstance().signInWithEmailAndPassword("employee001@gmail.com","000000");
        /*OfficialID tempId=new OfficialID("Warden1","EE","Warden","EMP001",
                "employee001@gmail.com","9509126582",getString(R.string.hostel_id));
        FirebaseDatabase.getInstance().getReference(getString(R.string.college_id))
        .child(getString(R.string.officials_id_ref)).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
        .setValue(tempId);
        createCredentials("Warden1");
        */

        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
            startActivity(new Intent(this,LoginAcitivity.class));
        else startActivity(new Intent(this, LoginAcitivity.class));


   /*     SweetAlertDialog showPermissionMessage=new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE).setTitleText("Please give permissions")
                .setContentText("Please provide the asked permissions so that we can provide you all the services").
                        setConfirmText("Try Again").
                        setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                if(isPermissionGranted())
                                {
                                    startActivity(new Intent(Splash.this, LoginAcitivity.class));
                                }
                                sweetAlertDialog.dismiss();
                            }
                        }).
                        setCancelText("Exit").
                        setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                startActivity(new Intent(Splash.this,LoginAcitivity.class));
                            }
                        });

        */






    }

    private void createCredentials(String Name) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(Name).build();
            user.updateProfile(profileUpdates);
        }
    }


    public boolean isPermissionGranted() {


        boolean okay = true;

        if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_CAMERA_REQUEST_CODE);
            okay = false;
        }else
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
            okay = false;
        }else
        if (checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_CODE);
            okay = false;
        }else
        if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE);
            okay = false;
        }else
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            okay = false;
        }else
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            okay = false;
        }else
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);
            okay = false;
        }
        else if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);
            okay = false;
        }
        else if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_CAMERA_REQUEST_CODE);
            okay = false;
        }







        return okay;
    }
}
