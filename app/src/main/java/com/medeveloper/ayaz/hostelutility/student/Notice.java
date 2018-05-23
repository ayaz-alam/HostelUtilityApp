package com.medeveloper.ayaz.hostelutility.student;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medeveloper.ayaz.hostelutility.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Notice extends Fragment {


    public Notice() {
        // Required empty public constructor
    }

    View rrotView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rrotView=inflater.inflate(R.layout.student_notice, container, false);









        return rrotView;
    }

}
