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
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.Timer;
import java.util.TimerTask;

public class Registration extends AppCompatActivity {

    public static final int EMPLOYEE = 111;
    public static final int STUDENT = 222;
    static int FLAG_STEP_NUMBER=1;
    MyData prefs;
    First first;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();
        prefs=new MyData(this);
        prefs.clearPreferences();
        first=new First();
        FragmentManager fn=getSupportFragmentManager();
        fn.beginTransaction().replace(R.id.fragment_layout, first, "First").commit();


    }


}
