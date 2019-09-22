package com.medeveloper.ayaz.hostelutility;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.MyData;
import com.medeveloper.ayaz.hostelutility.officials.OfficialsHome;
import com.medeveloper.ayaz.hostelutility.student.Home;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class Splash extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 00;
    private static final int REQUEST_CODE = 222;




    static {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    private int PERMISSION_REQUESTS =111;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        final String [] permissions=new String []
                {
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(isPermissionGranted()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        if(FirebaseAuth.getInstance().getCurrentUser().isAnonymous())
                        {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(Splash.this,LoginAcitivity.class));
                            finish();
                        }
                        else
                        if (new MyData(Splash.this).getData(MyData.EMPLOYEE_ID).equals("NULL")) {
                            startActivity(new Intent(Splash.this, Home.class));
                            finish();
                        } else {
                            startActivity(new Intent(Splash.this, OfficialsHome.class));
                            finish();
                        }
                    } else {
                        startActivity(new Intent(Splash.this, LoginAcitivity.class));
                        finish();
                    }
                }
            }, SPLASH_DISPLAY_LENGTH);

        }
        else
        {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUESTS);

        }


    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isPermissionGranted() {


        Log.d("Splash","came in Permission");
        boolean okay = true;




        if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            okay = false;
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            okay = false;
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            okay = false;
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            okay = false;
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            okay = false;
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            okay = false;
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
             okay = false;
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            okay = false;
        }

        return okay;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        Log.d("Splash","Came in request result: code is"+requestCode);
        if(requestCode==PERMISSION_REQUESTS)
        {
            if(!isPermissionGranted())
            {
                new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Permission required")
                        .setContentText("Please give all the required permission")
                        .setConfirmText("Grant")
                        .setCancelText("Exit")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                ActivityCompat.requestPermissions(Splash.this, permissions, PERMISSION_REQUESTS);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finish();
                            }
                        }).show();
            }
            else
            {
             startActivity(new Intent(this,LoginAcitivity.class));
            }
        }

    }
}
