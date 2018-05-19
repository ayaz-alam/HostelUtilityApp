package com.medeveloper.ayaz.hostelutility.student;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.medeveloper.ayaz.hostelutility.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DietOff extends Fragment implements DatePickerDialog.OnDateSetListener{


    public DietOff() {
        // Required empty public constructor
    }

    View rootView;
    Boolean from=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Calendar mCurrentTime = Calendar.getInstance();
        int year = mCurrentTime.get(Calendar.YEAR);
        int month = mCurrentTime.get(Calendar.MONTH);
        int day = mCurrentTime.get(Calendar.DAY_OF_MONTH);
        rootView=inflater.inflate(R.layout.student_diet_off, container, false);



        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),DietOff.this, year, month, day);
        (rootView.findViewById(R.id.from
        ))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        datePickerDialog.show();
                    }
                });

        (rootView.findViewById(R.id.to
        )).setEnabled(false);

        (rootView.findViewById(R.id.to
        )).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        datePickerDialog.show();
                    }
                });

        return rootView;//inflater.inflate(R.layout.student_complaint, container, false);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        String selectedDate = dayOfMonth + " " + getMonthForInt(month) + " " + year;

        if(!from)
        {
        ((TextView)rootView.findViewById(R.id.from)).setText(selectedDate);
            (rootView.findViewById(R.id.to)).setEnabled(true);
            from=!from;
        }
        else
        {
            ((TextView)rootView.findViewById(R.id.to)).setText(selectedDate);
        }


    }

    String getMonthForInt(int num) {
        String month = "wrong";
        num--;
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

}
