package com.medeveloper.ayaz.hostelutility.officials;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medeveloper.ayaz.hostelutility.R;


public class GeneralNotice extends Fragment {

    public GeneralNotice() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_general_notice, container, false);
    }


}
