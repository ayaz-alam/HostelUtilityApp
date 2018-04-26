package com.medeveloper.ayaz.hostelutility.student;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.medeveloper.ayaz.hostelutility.R;


public class ComplaintFragment extends Fragment {


    public ComplaintFragment() {
        // Required empty public constructor
    }

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.student_complaint, container, false);
        Toast.makeText(getActivity(),"Came in complaint fragment",Toast.LENGTH_SHORT).show();

        return rootView;//inflater.inflate(R.layout.student_complaint, container, false);
    }



}
