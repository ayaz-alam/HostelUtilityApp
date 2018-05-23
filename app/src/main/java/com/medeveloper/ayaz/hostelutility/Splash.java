package com.medeveloper.ayaz.hostelutility;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.medeveloper.ayaz.hostelutility.student.StudentForm;

public class Splash extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


                startActivity(new Intent(Splash.this,LoginAcitivity.class));

                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
