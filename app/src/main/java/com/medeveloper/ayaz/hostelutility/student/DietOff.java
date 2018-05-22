package com.medeveloper.ayaz.hostelutility.student;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medeveloper.ayaz.hostelutility.R;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.DietOffRequestClass;
import com.medeveloper.ayaz.hostelutility.classes_and_adapters.StudentDetailsClass;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class DietOff extends Fragment implements DatePickerDialog.OnDateSetListener{


    StudentDetailsClass myDetails;
    public DietOff() {
        // Required empty public constructor
    }

    View rootView;
    Boolean from=false;
    int DaysLeft=7;
    int FromDate;
    int Month;
    int DaysRequested;
    Date choosenDate;
    DatabaseReference baseRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Calendar mCurrentTime = Calendar.getInstance();
        int year = mCurrentTime.get(Calendar.YEAR);
        int month = mCurrentTime.get(Calendar.MONTH);
        final int day = mCurrentTime.get(Calendar.DAY_OF_MONTH);
        rootView=inflater.inflate(R.layout.student_diet_off, container, false);

        final Button submit=rootView.findViewById(R.id.submit_request);
        final TextView daysLeft=rootView.findViewById(R.id.days_left);
        final EditText details=rootView.findViewById(R.id.reason_diet_off);

        baseRef= FirebaseDatabase.getInstance().getReference(getString(R.string.college_id)).child(getString(R.string.hostel_id));
        baseRef.child(getString(R.string.mess_days_left_ref)).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    DaysLeft=dataSnapshot.getValue(Integer.class);
                else DaysLeft=0;

                daysLeft.setText((7-DaysLeft)+" Days");
                if(DaysLeft>6)
                {
                    daysLeft.setText("0 Days");
                    DaysLeft=7;
                    ShowDialog("You don't have any diet off days left",0);
                    submit.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),DietOff.this, year, month, day);
        (rootView.findViewById(R.id.from
        ))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Date today = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(today);// Subtract 6 months
                        long minDate = c.getTime().getTime(); // Twice!

                        datePickerDialog.show();
                        datePickerDialog.getDatePicker().setMinDate(minDate);
                    }
                });

        (rootView.findViewById(R.id.to
        )).setEnabled(false);

        final DatePickerDialog datePicker = new DatePickerDialog(getActivity(),DietOff.this, year, month, day);
        (rootView.findViewById(R.id.to
        )).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar c = Calendar.getInstance();
                        c.setTime(choosenDate);
                        c.add(Calendar.DAY_OF_MONTH,+1);
                        long minDate=c.getTime().getTime();
                        c.add(Calendar.DAY_OF_MONTH,+(6-DaysLeft));
                        long maxDate=c.getTime().getTime();

                        datePicker.getDatePicker().setMinDate(minDate);
                        datePicker.getDatePicker().setMaxDate(maxDate);//*/
                        datePicker.show();

                    }
                });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String To=((TextView)rootView.findViewById(R.id.to
                )).getText().toString();
                final String From=((TextView)rootView.findViewById(R.id.from
                )).getText().toString();
                final String Reason=details.getText().toString();


                if(isOkay(To,From,Reason))
                {
                    baseRef.child(getString(R.string.student_list_ref)).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists())
                                    {
                                        myDetails=dataSnapshot.getValue(StudentDetailsClass.class);
                                        Date currentTime = Calendar.getInstance().getTime();
                                        DietOffRequestClass myRequest=new DietOffRequestClass(myDetails.Name,myDetails.RoomNo,myDetails.EnrollNo,To,From,Reason,currentTime,false);
                                        baseRef.child(getString(R.string.diet_off_complaints_ref)).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(myRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                {
                                                    baseRef.child(getString(R.string.mess_days_left_ref))
                                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .setValue((DaysLeft+DaysRequested));
                                                    details.setText(null);
                                                    ShowDialog("Successfull",0);
                                                    ((TextView)(rootView.findViewById(R.id.to))).setText("Select Date");
                                                    ((TextView)(rootView.findViewById(R.id.from))).setText("Select Date");
                                                    ((rootView.findViewById(R.id.to))).setEnabled(false);


                                                }
                                            }
                                        });
                                    }


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            }
                    );


                }



            }
        });

        return rootView;//inflater.inflate(R.layout.student_complaint, container, false);
    }

    private boolean isOkay(String to, String from, String reason) {
        boolean okay=true;

        if(to.equals("Select Date"))
        {
            okay=false;
            ShowDialog("Please Select Date",0);
        }
        else if(from.equals("Select Date"))
        {
            okay=false;
            ShowDialog("Please Select Date",0);
        }
        else if(reason.equals(""))
        {
            okay=false;
            ShowDialog("Please give the reason of the leave",0);
        }
        else if(reason.length()<50)
        {
            okay=false;
            ShowDialog("Reason should be of atleast 50 charactors",0);
        }

    return okay;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        String selectedDate = dayOfMonth + " " + getMonthForInt(month) + " " + year;

        if(!from)
        {
            Calendar c=Calendar.getInstance();
            FromDate=dayOfMonth;
            Month=month;
            c.set(year,month-1,dayOfMonth,0,0);
            choosenDate=c.getTime();

        ((TextView)rootView.findViewById(R.id.from)).setText(selectedDate);
            (rootView.findViewById(R.id.to)).setEnabled(true);
            from=!from;
        }
        else
        {
            if(Month!=month)
            DaysRequested=(30-FromDate)+dayOfMonth;
            else DaysRequested=dayOfMonth-FromDate;

            ((TextView)rootView.findViewById(R.id.to)).setText(selectedDate);
        }


    }

    private void ShowDialog(String msg, int code) {

        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
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
