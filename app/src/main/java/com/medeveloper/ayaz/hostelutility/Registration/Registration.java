package com.medeveloper.ayaz.hostelutility.Registration;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.MyData;
import com.medeveloper.ayaz.hostelutility.interfaces.onCompletionListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

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
    Button mRegistrationButton;
    private void initRegistrationButton() {
        mRegistrationButton = (findViewById(R.id.reg_button))
                ;
        mRegistrationButton.setOnClickListener(new View.OnClickListener() {
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

        final SweetAlertDialog pDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Please wait..");

        if(FLAG_STEP_NUMBER==1)
        {
            pDialog.show();
            mRegistrationButton.setEnabled(false);
            first.canProceed(new onCompletionListener(){

                @Override
                public void onComplete(boolean complete) {
                    mRegistrationButton.setEnabled(true);
                    pDialog.dismiss();
                    if(complete) {
                        FLAG_STEP_NUMBER++;
                        FragmentManager fn = getSupportFragmentManager();
                        fn.beginTransaction().replace(R.id.fragment_layout, second, "Second").commit();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Error: "+complete,Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else if(FLAG_STEP_NUMBER==2)
        {
            pDialog.show();
            mRegistrationButton.setEnabled(false);
            second.canProceed(new onCompletionListener(){

                @Override
                public void onComplete(boolean complete) {
                    if(complete) {
                        mRegistrationButton.setEnabled(true);
                        pDialog.dismiss();
                        FLAG_STEP_NUMBER++;
                        FragmentManager fn = getSupportFragmentManager();
                        fn.beginTransaction().replace(R.id.fragment_layout, third, "Third").commit();
                    }
                    else
                        {
                        pDialog.dismissWithAnimation();
                        Toast.makeText(getApplicationContext(),"Error: "+complete,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else if(FLAG_STEP_NUMBER==3)
        {
            if(third.canProceed())
            {


            }

        }

    }
}
