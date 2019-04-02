package com.medeveloper.ayaz.hostelutility.Registration;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.MyData;
import com.medeveloper.ayaz.hostelutility.student.Notice;
import com.medeveloper.ayaz.hostelutility.student.StudentProfile;

public class Registration extends AppCompatActivity {

    public static final int EMPLOYEE = 111;
    public static final int STUDENT = 222;
    int FLAG_STEP_NUMBER=0;
    MyData prefs;
    First first;
    private Second second;
    private Third third;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();
        prefs=new MyData(this);
        prefs.clearPreferences();
        first=new First();
        second = new Second();
        third = new Third();
        initRegistrationButton();
        FLAG_STEP_NUMBER++;
        FragmentManager fn=getSupportFragmentManager();
        fn.beginTransaction().replace(R.id.fragment_layout, first, "First").commit();


    }

    /**
     * This function is to initialize the register button of the container activity
     */
    private void initRegistrationButton() {
        (findViewById(R.id.reg_button))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setUpButton();
                    }
                });
    }

    /**
     * This function decidesd the behaviour of the registration button
     */
    private void setUpButton() {


        if(FLAG_STEP_NUMBER==1)
        {
            if(first.canProceed()) {
                FLAG_STEP_NUMBER++;
                FragmentManager fn = getSupportFragmentManager();
                fn.beginTransaction().replace(R.id.fragment_layout, second, "Second").commit();
            }
            else Toast.makeText(this,"Error: "+first.canProceed(),Toast.LENGTH_SHORT).show();
        }
        else if(FLAG_STEP_NUMBER==2)
        {
            if(second.canProceed()) {
                FLAG_STEP_NUMBER++;
                FragmentManager fn = getSupportFragmentManager();
                fn.beginTransaction().replace(R.id.fragment_layout, third, "Third").commit();
            }
        }
        else if(FLAG_STEP_NUMBER==3)
        {
            if(third.canProceed())
            {


            }

        }

    }
}
