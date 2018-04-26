package com.medeveloper.ayaz.hostelutility.officials;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medeveloper.ayaz.hostelutility.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DietOffRequests extends Fragment {


    public DietOffRequests() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.officials_diet_off_requests, container, false);
    }

}
