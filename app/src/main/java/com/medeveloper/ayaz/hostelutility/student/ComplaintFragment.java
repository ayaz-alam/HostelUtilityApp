package com.medeveloper.ayaz.hostelutility.student;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.medeveloper.ayaz.hostelutility.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;


public class ComplaintFragment extends Fragment implements DatePickerDialog.OnDateSetListener {


    public ComplaintFragment() {
        // Required empty public constructor
    }

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.student_complaint, container, false);


        final Calendar mCurrentTime = Calendar.getInstance();
        int year = mCurrentTime.get(Calendar.YEAR);
        int month = mCurrentTime.get(Calendar.MONTH);
        int day = mCurrentTime.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),ComplaintFragment.this, year, month, day);
        (rootView.findViewById(R.id.date))
                .setOnClickListener(new View.OnClickListener() {
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
        ((TextView)rootView.findViewById(R.id.date)).setText(selectedDate);



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
