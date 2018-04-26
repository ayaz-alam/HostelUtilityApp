package com.medeveloper.ayaz.hostelutility;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.medeveloper.ayaz.hostelutility.officials.OfficialsHome;
import com.medeveloper.ayaz.hostelutility.student.Home;

public class LoginAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);


        (findViewById(R.id.student)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Home.class));
            }
        });
        (findViewById(R.id.officials)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),OfficialsHome.class));
            }
        });
    }
}
