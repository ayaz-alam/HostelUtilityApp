package com.medeveloper.ayaz.hostelutility;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class About extends Fragment {


    private static final int MY_CAMERA_REQUEST_CODE = 121;
    private static final int REQUEST_CODE = 23;

    public About() {
        // Required empty public constructor
    }


    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.about, container, false);



        return rootView;
    }

}
